package mt_inside.spamager

import scala.concurrent._
import scala.util.{Success, Failure}
import scala.concurrent.duration._
import akka.actor.Actor
import akka.event.Logging
import akka.pattern.ask
import akka.io.IO
import spray.http._
import spray.client.pipelining._
import spray.can.Http
import spray.util._

class Adder( lanager_url : String, cookie : String, token : String ) extends Actor
{
  implicit val system = context.system;
  import context.dispatcher; // execution context for futures

  val log = Logging(context.system, getClass);

  val url = lanager_url + "/playlists/1/items";


  /* TODO I thought akka favoured actors to futures, anyway to get it to message an actor on reponse rather than return this future? */
  val pipeline: HttpRequest => Future[HttpResponse] =
  (
    addHeader( "Referer", url )
    ~> addHeader( "Cookie", "lanager=" + cookie )
    ~> sendReceive
  );

  def receive =
  {
    /* lol typesafety */
    case video : String =>
      val formData = Seq(
        "_token" -> token,
        "url" -> video
      );
      val response: Future[HttpResponse] =
        pipeline(
          Post(
            url,
            FormData( formData )
          )
        );

      response onComplete {
        case Success(HttpResponse(rc, body, headers, protocol)) =>
          log.info("Spam successful; HTTP code " + rc);
          println(body.asString);
          shutdown();

        case Failure(error) =>
          log.error(error, "Video post failed");
          shutdown();
      }
  };

  def shutdown(): Unit = {
    IO(Http).ask(Http.CloseAll)(1.second).await;
    // TODO send message back to parent, telling it all done
    //system.shutdown();
  };
}
