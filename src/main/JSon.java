package main;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;


public class JSon {
    private Optional<JSONArray> commentsArray;
    private Optional<JSONArray> todosArray;

    public int getCommentsArraySize(){
        return commentsArray.get().size();
    }

    public int getTodosArraySize(){
        return todosArray.get().size();
    }

    public static void main(String[] args){
        JSon a = new JSon();
        System.out.println(a.getData().get());
    }
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }



    protected Optional<Integer> getData() {
        try{
            URL commentsDataUrl = new URL("http://jsonplaceholder.typicode.com/comments");
            String dataFromUrl = readDataFromUrl(commentsDataUrl);
            commentsArray = parseDataForArray(dataFromUrl);

            URL todoDataUrl = new URL("http://jsonplaceholder.typicode.com/todos");
            String todoDataString = readDataFromUrl(todoDataUrl);
            todosArray = parseDataForArray(todoDataString);

            return calculateData();

        }catch (MalformedURLException e){
            System.out.println("Malformed url in getData");
        }
        return Optional.empty();
    }

    public Optional<Integer> calculateData(){
        if (commentsArray.isPresent() && todosArray.isPresent()){
            return Optional.of(getCommentsArraySize() - getTodosArraySize());
        }
        return Optional.empty();
    }

    public Optional<JSONArray> parseDataForArray(String stringDataFromUrl){
        Optional<JSONArray> arrayObject;
        try{
            JSONParser parser = new JSONParser();
            arrayObject = Optional.ofNullable((JSONArray)parser.parse(stringDataFromUrl));
        }catch (ParseException e){
            System.out.println("Error happened during response parsing");
            arrayObject = Optional.empty();
        }
        return arrayObject;
    }

    public String readDataFromUrl(URL url){
        String dataFromUrl = "";
        Optional<InputStream> inputStreamOptional = openStreamFromUrl(url);
        if(inputStreamOptional.isPresent()){
            try{
                InputStream dataInputStream = inputStreamOptional.get();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
                dataFromUrl = readAll(bufferedReader);
            }catch(IOException e){
                System.out.println("IO Exception in readDataFromUrl method.");
            }
        }
        return dataFromUrl;
    }

    public Optional<InputStream> openStreamFromUrl(URL url){
        try{
            return Optional.ofNullable(url.openStream());
        }catch (IOException e){
            System.out.println("Cant open stream from url in openStreamFromUrl method.");
        }
        return Optional.empty();
    }
}
