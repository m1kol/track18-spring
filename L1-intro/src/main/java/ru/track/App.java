package ru.track;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.HttpRequestWithBody;

/**
 * TASK:
 * POST request to  https://guarded-mesa-31536.herokuapp.com/track
 * fields: name,github,email
 *
 * LIB: http://unirest.io/java.html
 *
 *
 */
public class App {

  public static final String URL = "http://guarded-mesa-31536.herokuapp.com/track";
  public static final String FIELD_NAME = "name";
  public static final String FIELD_GITHUB = "github";
  public static final String FIELD_EMAIL = "email";

  public static void main(String[] args) throws Exception {
    // 1) Use Unirest.post()
    // 2) Get response .asJson()
    // 3) Get json body and JsonObject
    // 4) Get field "success" from JsonObject

    HttpResponse<JsonNode> postRquest = Unirest.post(URL)
      .field(FIELD_NAME, "Mikhail")
      .field(FIELD_EMAIL, "misha.kolesov98@gmail.com")
      .field(FIELD_GITHUB, "m1kol")
      .asJson();

    System.out.println (postRquest.getBody().getObject().get("success"));


    boolean success = false;
  }


}
