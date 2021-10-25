package major.adam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

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
}
