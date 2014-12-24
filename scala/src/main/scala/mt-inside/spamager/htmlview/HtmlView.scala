package mt_inside.spamager.htmlview

import akka.actor.Actor
import spray.routing._
import spray.http._
// implicit marshallers from Play's render() result types
// (play.twirl.api.{Html,Txt} to HttpResponse
import spray.httpx.PlayTwirlSupport._
import MediaTypes._

import mt_inside.spamager.model._

class HtmlView extends Actor with HtmlViewService
{
  def actorRefFactory = context;

  def receive = runRoute( routes );
}

trait HtmlViewService extends HttpService
{
  val videos = Seq( Video( "http://lol" ), Video( "http://bar" ) );

  /* Twirl file names must be of the form: foo.scala.{html,txt,etc}.
   * The objects that then magically appear are:
   *   {html,txt,etc}.foo
   * e.g.
   *   html.index.render( args );
   * Files can be namespaced in the twirl directory like
   *   src/main/twirl/org/mt_inside/index.scala.txt
   * and the resulting object is
   *   org.mt_inside.txt.index
   */
  val routes =
    path("") {
      get {
        complete {
          html.index.render( videos );
        }
      }
    }
}
