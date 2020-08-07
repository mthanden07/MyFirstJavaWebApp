
  package net.myfirst.webapp;

//import net.myfirst.webapp.index.html;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark. *;
import static spark.Spark.post;


public class App {


    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }




    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        int count = 0;
        List<String> list = new ArrayList<>();

        staticFiles.location("/public/");
        get("/greet", (req, res) -> "Hello");
        get("/greet/:username", (req, res) -> {
            return "Hello  " + req.params(":username");
        });
       /* get("/greet/:username/language/:language", (req, res) -> {
            return "Hello  " + req.params("username") + "  How are you ?";
        });*/

        get("/greet/:username/language/:language", (req, res) -> {
            if (req.params(":language").equals("English")) {
                return "Hello  " + req.params("username") + "  How are you ?";
            } else if (req.params(":language").equals("IsiZulu")) {
                return "Sawubona  " + req.params("username") + "  Unjani ?";
            }
        else if (req.params(":language").equals("IsiXhosa")) {
            return "Molweni  " + req.params("username") + "  Kunjani ?";
        }
            else if (req.params(":language").equals("Sotho")) {

                return "Dumelang  " + req.params("username") + "  Ojwang ?";
            }
          else  if (req.params(":language").equals("TsotsiTal")){
              return "Eita da !!  " + req.params("username") + "  Unjani Skhokho ?";
            }
          else return "What are you trying to happen ?";
        });
       // stop();

        post("/greet",(req,response)->{
            //System.out.println("Hello");
            return  "Hello!!!!  " +  req.queryParams("username");

        });

        get("/hello", (req, res) ->{

            
            Map<String, Object> map = new HashMap<>();
            map.put("count", 2);

            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());


        post("/hello", (req, res) -> {

            Map<String, Object> map = new HashMap<>();

            // create the geeting message
            String greeting = "Hello, " + req.queryParams("username");

            // put it in the map which is passed to the template - the value will be merged into the template
            map.put("greeting", greeting);
            map.put("count", list.size());
            list.add(req.queryParams("username"));

            return new ModelAndView(map, "hello.handlebars");

        }, new HandlebarsTemplateEngine());

        post("/greeted/:username",(req,response)->{
            int greeted = 0;
            for(String user : list){
                if(user.equals(req.queryParams("username"))){
                    greeted++;
                }
            }
            //System.out.println("Hello");
            return  "You have been greeted  " +  greeted;

        });
      }
}