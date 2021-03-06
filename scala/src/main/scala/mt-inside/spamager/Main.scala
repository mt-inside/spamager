package mt_inside.spamager

import scala.concurrent.duration._
import akka.actor._
import akka.event.Logging
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import spray.can.Http

import htmlview.HtmlView

object Main extends App
{
  implicit val system = ActorSystem( );
  val log = Logging( system, getClass );

  val lanager_url = "http://empty.org.uk:12345";
  val cookie = "eyJpdiI6IllLYzZhVEl3Zm5zYXBCQk1tSmloUmdEWGNNbHZTTDlWdU9VQWY2QTYrK0U9IiwidmFsdWUiOiJoeGtWK1FcL1BaaGx3ODJFMWtFQnhFbys2MitKeEtQZlB5WTRiSFwvYjQ2dFdIbXZReXdvVVhoSWFiODhDYXI0OXhnYzBqejlBUktQQ0llSlwvTXdFc2J5UT09IiwibWFjIjoiOWMzOGQ0YzMzNTU5ZGIyOGFlNDRhNmRjYWU1NWRjY2IwNWM3MTE4MDgwMGQ3ZjUzOGZhMjc5MTZhMDA0OTJlZiJ9";
  val token = "LU9hOHMpJF1H9mIfYJr4oEMMJX1P9aP4Lz0hJ2ZK";
  val adder = system.actorOf( Props ( new Adder( lanager_url, cookie, token ) ), name = "Adder" );

  val video = "https://www.youtube.com/watch?v=dOyJqGtP-wU";
  //adder ! video;


  implicit val timeout = Timeout(5.seconds);
  val htmlView = system.actorOf( Props[HtmlView], name = "HtmlView" );
  IO(Http) ? Http.Bind( htmlView, interface = "localhost", port = 8080 );
}
