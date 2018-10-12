/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kysymyspankki;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author Samuli
 */
public class Kysymyspankki {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        String dbadd = System.getenv("JDBC_DATABASE_URL");

        Database database = new Database(dbadd);
        KysymysDao kysymysDao = new KysymysDao(database);
        VastausDao vastausDao = new VastausDao(database);

        Spark.get("/", (req, res) -> {
            List<Kysymys> kysymykset = kysymysDao.findAll();

            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymykset);

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/uusikysymys", (req, res) -> {

            kysymysDao.saveOrUpdate(new Kysymys(-1, req.queryParams("kurssi"), req.queryParams("aihe"), req.queryParams("kysymysteksti")));

            res.redirect("/");

            return "";
        });

        Spark.post("/delete/:id", (req, res) -> {

            Integer id = Integer.parseInt(req.params(":id"));
            kysymysDao.delete(id);

            res.redirect("/");

            return "";

        });

        Spark.get("/kysymys/:id", (req, res) -> {
            
            HashMap map = new HashMap<>();
            
            Integer id = Integer.parseInt(req.params(":id"));
            
            map.put("vastaukset", kysymysDao.findVastaukset(id));
            
            return "";
        });

        Spark.post("/kysymys/:id/uusi", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));
           
            
            vastausDao.saveOrUpdate(new Vastaus (-1, id, req.queryParams("vastaus"), Boolean.parseBoolean(req.queryParams("oikein"))));
            
            res.redirect("/");
            return "";
            
        });

        Spark.post("/vastaus/:id", (req, res) -> {
            Integer id = Integer.parseInt(req.params(":id"));

            vastausDao.delete(id);

            res.redirect("/");

            return "";

        });

    }

}
