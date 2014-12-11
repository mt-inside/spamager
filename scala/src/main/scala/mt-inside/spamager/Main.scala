package mt_inside.spamager

import akka.actor._
import akka.event.Logging

object Main extends App
{
  implicit val system = ActorSystem( );
  val log = Logging( system, getClass );

  val lanager_url = "http://empty.org.uk:12345";
  val cookie = "eyJpdiI6IllLYzZhVEl3Zm5zYXBCQk1tSmloUmdEWGNNbHZTTDlWdU9VQWY2QTYrK0U9IiwidmFsdWUiOiJoeGtWK1FcL1BaaGx3ODJFMWtFQnhFbys2MitKeEtQZlB5WTRiSFwvYjQ2dFdIbXZReXdvVVhoSWFiODhDYXI0OXhnYzBqejlBUktQQ0llSlwvTXdFc2J5UT09IiwibWFjIjoiOWMzOGQ0YzMzNTU5ZGIyOGFlNDRhNmRjYWU1NWRjY2IwNWM3MTE4MDgwMGQ3ZjUzOGZhMjc5MTZhMDA0OTJlZiJ9";
  val token = "LU9hOHMpJF1H9mIfYJr4oEMMJX1P9aP4Lz0hJ2ZK";
  val adder = system.actorOf( Props ( new Adder( lanager_url, cookie, token ) ), name = "Adder" );

  val video = "https://www.youtube.com/watch?v=dOyJqGtP-wU";
  adder ! video;
}
