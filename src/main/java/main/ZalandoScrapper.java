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
            //old
            //elements = doc.select("script#z-vegas-pdp-props");
            
            elements = doc.select("script.re-1-12");
            for (Element element : elements) {
                if (element.childNodeSize() > 0) {
                    String json = element.childNode(0).toString();
                    JSONObject object = (JSONObject) new JSONParser().parse(json);
                    JSONArray array = null;
                    
                    object = (JSONObject) object.get("graphqlCache");
                    for(Object key:object.keySet()){
                        String keyS = key.toString();
                        if(keyS.startsWith("{\"id\":\"3cbcb551386476489f3ea05d361fc0c6a15c13fb7678d667fc79952db14f23fa\"")){
                            JSONObject obj = (JSONObject) object.get(keyS);
                            obj = (JSONObject) obj.get("data");
                            obj = (JSONObject) obj.get("product");
                            
                            shoe.name = ((String) obj.get("sku"))+ " " +((String) obj.get("name"));
                            array = (JSONArray) obj.get("simples");
                            array.forEach(arrObj->{
                                ShoeSize shoeSize = new ShoeSize();

                                JSONObject curObj = (JSONObject)arrObj;
                                shoeSize.id = (String) curObj.get("sku");
                                
                                shoeSize.size = (String) curObj.get("size");
                                JSONObject offer = (JSONObject) curObj.get("offer");
                                shoeSize.setStock((String)((JSONObject) offer.get("stock")).get("quantity"));
                                JSONObject priceOriginal = (JSONObject)((JSONObject)offer.get("price")).get("original");
                                shoeSize.price = (Long)priceOriginal.get("amount") + (String)priceOriginal.get("currency") + "";
                                shoeSizes.add(shoeSize);
                            });
                            
                            shoe.shoeSizes = shoeSizes;
                            shoe.shopLink = searchUrl;
                            array = (JSONArray) obj.get("media");
                            obj = (JSONObject) array.get(0);
                            shoe.imageLink = (String)obj.get("uri");
                            break;
                        }                        
                    }
                }
            }
        } catch (IOException | ParseException ex) {
            System.err.println(ex.getMessage());
        }
        
        return shoe;
    }
}