package major.adam;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.json.simple.DeserializationException;
import org.json.simple.JsonObject;
import org.json.simple.Jsonable;
import org.json.simple.Jsoner;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        try {
            URI absoluteUri = new URI("http://username:password@myserver.com:5000/catalogue/phones?os=android#samsung");
            printURIParts(absoluteUri);
            System.out.println("-".repeat(50));
            System.out.println("absoluteURI.toURL() = " + absoluteUri.toURL());

            //best to separate base from relative URI
            URI baseUri = new URI("http://username:password@myserver.com:5000");
            URI relativeUri = new URI("/catalogue/phones?os=android#samsung");
            URI resolvedURL = baseUri.resolve(relativeUri);
            System.out.println("resolvedURI.toURL() = " + resolvedURL.toURL());

            System.out.println("isEqual = " + resolvedURL.toString().equals(absoluteUri.toString()));
            
            //getting relative URI from absolute
            URI relativizedURI = baseUri.relativize(resolvedURL);
            System.out.println("relativizedURI = " + relativizedURI);


            //actually accessing a webpage using url.openStream()
            System.out.println("-".repeat(50));
            URL url = new URL("http://example.org");
            URI uri = url.toURI();
            printURIParts(uri);

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while((line = inputStream.readLine()) != null) {
                System.out.println(line);
            }

            inputStream.close();


            //using URLConnection
            URL url2 = new URL("http://example.org");

            URLConnection urlConnection = url2.openConnection();

            //trying change to write rather than default of read
            urlConnection.setDoOutput(true);

            //must do all config of urlConnection before this
            //many methods in class only applicable to Http connections
            //so better to use HttpURLConnection when working with Http connections
            urlConnection.connect();

            BufferedReader inputStream2 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line2 = "";
            while ((line2 = inputStream2.readLine()) != null) {
                System.out.println("line = " + line2);
            }


            //getting headers without reading whole page
            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            headerFields.forEach((key, values) -> {
                System.out.println("key = " + key);

                for(String value : values) {
                    System.out.println("value = " + value);
                }
            });


            //using the HttpURLConnection class
            //each HttpURLConnection instance can only make one request.
            //the underlying connection may exist but need to make a new instance of HttpURLConnection each time a request is made
            URL flickrURL = new URL("https://www.flickr.com/services/feeds/photos_public.gne");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) flickrURL.openConnection();
            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setRequestProperty("User-Agent", "Java test");

            int responseCode = httpsURLConnection.getResponseCode();
            httpsURLConnection.setReadTimeout(10000);

            System.out.println("responseCode = " + responseCode);
            if (responseCode != 200) {
                System.out.println("Error getting jokes...");
                return;
            }

            String joke = "";
            BufferedReader jokeStream = new BufferedReader(new InputStreamReader(httpsURLConnection.getInputStream()));
            while((joke = jokeStream.readLine()) != null) {
                System.out.println("joke = " + joke);
            }

            postRequestExample();

            //Consider using Jetty or Apache HTTPClient libraries instead of above
            apacheHttpClientExample();



        } catch (URISyntaxException e) {
            System.out.println("URI bad syntax: " + e.getMessage());
        } catch (MalformedURLException e) {
            System.out.println("URL malformed: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printURIParts(URI uri) {
        System.out.println("Scheme = " + uri.getScheme());
        System.out.println("Scheme-specific part = " + uri.getSchemeSpecificPart());
        System.out.println("Authority = " + uri.getAuthority());
        System.out.println("UserInfo = " + uri.getUserInfo());
        System.out.println("Host = " + uri.getHost());
        System.out.println("Port = " + uri.getPort());
        System.out.println("Path = " + uri.getPath());
        System.out.println("Query = " + uri.getQuery());
        System.out.println("Fragment = " + uri.getFragment());
    }

    private static void postRequestExample(){
        System.out.println("-".repeat(50));

        try {
            URL registerUrl = new URL("https://still-bayou-51404.herokuapp.com/register");
            HttpsURLConnection connection = (HttpsURLConnection) registerUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String parameters = "username=Test123&email=testt@gmail.com&password=test&reEnterPassword=test";
            connection.setRequestProperty("Content-Length", Integer.toString(parameters.getBytes().length));

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(parameters);
            output.flush();
            output.close();

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println("line = " + line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void apacheHttpClientExample() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://icanhazdadjoke.com");
        request.addHeader("User-Agent", "Java test");
        request.addHeader("Accept", "text/plain");

        CloseableHttpResponse response = null;
        BufferedReader bufferedReader = null;
        try {
            response = httpClient.execute(request);
            System.out.println("Response Code: " + response.getCode());
            System.out.println("response = " + response);

            bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String jsonResponse = bufferedReader.readLine();
            System.out.println("jsonResponse = " + jsonResponse);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
