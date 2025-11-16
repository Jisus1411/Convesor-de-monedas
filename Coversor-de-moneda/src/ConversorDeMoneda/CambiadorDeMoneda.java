package ConversorDeMoneda;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class CambiadorDeMoneda {

    public static final Map<String, String> NOMBRES_MONEDAS = new HashMap<>();
    static {
        NOMBRES_MONEDAS.put("USD", "Dólar estadounidense");
        NOMBRES_MONEDAS.put("EUR", "Euro");
        NOMBRES_MONEDAS.put("GBP", "Libra esterlina");
        NOMBRES_MONEDAS.put("JPY", "Yen japonés");
        NOMBRES_MONEDAS.put("CAD", "Dólar canadiense");
        NOMBRES_MONEDAS.put("AUD", "Dólar australiano");
        NOMBRES_MONEDAS.put("CHF", "Franco suizo");
        NOMBRES_MONEDAS.put("CNY", "Yuan chino");
        NOMBRES_MONEDAS.put("COP", "Peso colombiano");
        NOMBRES_MONEDAS.put("MXN", "Peso mexicano");
        NOMBRES_MONEDAS.put("BRL", "Real brasileño");
        NOMBRES_MONEDAS.put("ARS", "Peso argentino");
        NOMBRES_MONEDAS.put("CLP", "Peso chileno");
        NOMBRES_MONEDAS.put("PEN", "Sol peruano");
    }

    public static double obtenerTasa(String monedaOrigen, String monedaDestino)
            throws IOException, InterruptedException {

        String apiKey = "9159207f2175cc9ed779b619";
        String urlFinal = "https://v6.exchangerate-api.com/v6/" + apiKey +
                "/pair/" + monedaOrigen + "/" + monedaDestino;

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlFinal))
                .GET()
                .build();

        HttpResponse<String> respuesta = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonElement elemento = JsonParser.parseString(respuesta.body());
        JsonObject objetoRaiz = elemento.getAsJsonObject();

        String baseCode = objetoRaiz.get("base_code").getAsString();
        String targetCode = objetoRaiz.get("target_code").getAsString();
        double tasa = objetoRaiz.get("conversion_rate").getAsDouble();

        String nombreBase = NOMBRES_MONEDAS.getOrDefault(baseCode, "Moneda desconocida");
        String nombreDestino = NOMBRES_MONEDAS.getOrDefault(targetCode, "Moneda desconocida");

        System.out.println("--------------------------------");
        System.out.println("Moneda base: " + baseCode + " - " + nombreBase);
        System.out.println("Moneda destino: " + targetCode + " - " + nombreDestino);
        System.out.println("Tasa de conversión actual: " + tasa);
        System.out.println("--------------------------------");

        return tasa;
    }
}
