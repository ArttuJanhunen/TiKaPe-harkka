
import java.io.File;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author arttu
 */
public class Main {

    public static void main(String[] args) throws Exception {
        File tiedosto = new File("db", "kymysys.db");
        Database database = new Database("jdbc:sqlite:" + tiedosto.getAbsolutePath());
        KysymysDao kysymykset = new KysymysDao(database);
        VastausDao vastaukset = new VastausDao(database);

        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        Spark.get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kysymykset.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaa", (req, res) -> {
            String kurssi = req.queryParams("kurssi");
            String aihe = req.queryParams("aihe");
            String kysymysteksti = req.queryParams("kysymysteksti");
            if (!kurssi.equals("") && !aihe.equals("") && !kysymysteksti.equals("")) {

                kysymykset.saveOrUpdate(new Kysymys(-1, kurssi, aihe, kysymysteksti));
            }

            res.redirect("/kysymykset");
            return "";
        });

        Spark.post("/poista/:id", (req, res) -> {
            Integer poistettava = Integer.parseInt(req.params(":id"));
            kysymykset.delete(poistettava);
            List<Vastaus> poistettavat = vastaukset.findAllwithKysymysId(poistettava);
            for (Vastaus vastaus : poistettavat) {
                vastaukset.delete(vastaus.getId());
            }

            res.redirect("/kysymykset");
            return "";
        });

        Spark.get("/kysymykset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            Integer kysymysId = Integer.parseInt(req.params(":id"));
            map.put("kysymys", kysymykset.findOne(kysymysId));
            map.put("vastaukset", vastaukset.findAllwithKysymysId(kysymysId));

            return new ModelAndView(map, "kysymys");
        }, new ThymeleafTemplateEngine());

        Spark.post("/lisaavastaus", (req, res) -> {
            int kysymysId = Integer.parseInt(req.queryParams("kysymys.id"));
            String vastausteksti = req.queryParams("vastausteksti");
            boolean totuusarvo = false;
            if (req.queryParams("totta") != null) {
                totuusarvo = true;
            }

            if (!vastausteksti.equals("")) {
                vastaukset.saveOrUpdate(new Vastaus(-1, kysymysId, vastausteksti, totuusarvo));
            }

            res.redirect("/kysymykset/" + kysymysId);
            return "";
        });

        Spark.post("/poistavastaus/:id", (req, res) -> {
            Integer poistettava = Integer.parseInt(req.params(":id"));
            vastaukset.delete(poistettava);
            int kysymysId = Integer.parseInt(req.queryParams("kysymys.id"));

            res.redirect("/kysymykset/" + kysymysId);
            return "";
        });

    }
}
