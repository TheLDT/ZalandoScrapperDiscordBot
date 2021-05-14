package main;

import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class ZalandoScrapper {
    private static ArrayList<ShoeSize> shoeSizes;
    private static Shoe shoe;
    public static Shoe find(String searchUrl) {
        String userAgent = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
        Document doc; 
        Elements elements;
        shoeSizes = new ArrayList<>();
        shoe = new Shoe();
        try {
            doc = Jsoup.connect(searchUrl).userAgent(userAgent).referrer("https://www.google.com/").timeout(10*10000).get();
            elements = doc.select("script#z-vegas-pdp-props");
            
            for (Element element : elements) {
                if (element.childNodeSize() > 0) {
                    String json = element.childNode(0).toString().substring(8,element.childNode(0).toString().length()-2);
                    JSONArray array = (JSONArray) new JSONParser().parse(json);
                    JSONObject object = (JSONObject) array.get(0);
                    object = (JSONObject) object.get("model");
                    object = (JSONObject) object.get("articleInfo");
                    shoe.name =  ((String) object.get("id"))+" " +((String) object.get("name"));
                    array = (JSONArray) object.get("units");
                    array.forEach(obj->{
                        ShoeSize shoeSize = new ShoeSize();
                        
                        JSONObject curObj = (JSONObject)obj;
                        shoeSize.id = (String) curObj.get("id");
                        shoeSize.stock = (Long) curObj.get("stock");
                        shoeSize.size = (String)((JSONObject)curObj.get("size")).get("local") + "";
                        shoeSize.price = (String)((JSONObject)curObj.get("price")).get("formatted") + "";
                        //System.out.println(shoe.toString());
                        
                        shoeSizes.add(shoeSize);
                    });
                    shoe.shoeSizes = shoeSizes;
                    object = (JSONObject)object.get("media");
                    array =(JSONArray) object.get("images");
                    object = (JSONObject)array.get(0);
                    object = (JSONObject)object.get("sources");
                    shoe.imageLink = (String)((JSONObject)object).get("reco2x");
                    shoe.shopLink = searchUrl;
                }
            }
        } catch (IOException | ParseException ex) {
            System.err.println(ex.getMessage());
        }
        
        return shoe;
    }
    
}