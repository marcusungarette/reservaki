import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LoadTest extends Simulation {

  val httpProtocol = http
    .baseUrl("http://app:8080")
    .acceptHeader("application/json")
    .contentTypeHeader("application/json")

  val restaurantPayload = StringBody("""
    {
      "name": "Restaurante do Chef",
      "location": "Rua Principal, 123",
      "cuisineType": "Italiana",
      "openingHours": "09:00-22:00",
      "capacity": 50
    }
  """)

  val scn = scenario("Restaurant Creation Load Test")
    .exec(
      http("Create Restaurant")
        .post("/api/restaurants")
        .header("Content-Type", "application/json")
        .body(restaurantPayload)
        .check(status.is(201))
    )

  setUp(
    scn.inject(atOnceUsers(100))
  ).protocols(httpProtocol)
}