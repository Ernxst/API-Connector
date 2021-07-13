package com.example.handler;

import com.example.services.AbstractServiceRequest;
import com.example.services.entertainment.BookSearchServiceRequest;
import com.example.services.entertainment.JokeServiceRequest;
import com.example.services.entertainment.NewsServiceRequest;
import com.example.services.finance.CharityByCityServiceRequest;
import com.example.services.finance.CharityBySearchServiceRequest;
import com.example.services.finance.StocksServiceRequest;
import com.example.services.food.RandomRecipeServiceRequest;
import com.example.services.food.RecipeByIngredientServiceRequest;
import com.example.services.food.RecipeBySearchServiceRequest;
import com.example.services.food.RecipeInstructionsService;
import com.example.services.schema.SchemaServiceFactory;
import com.example.services.transport.BusOrTrainBySearchServiceRequest;
import com.example.services.transport.NearestBusOrTrainServiceRequest;
import com.example.services.utility.AirQualityServiceRequest;
import com.example.services.utility.CurrentWeatherServiceRequest;
import com.example.services.utility.DictionaryServiceRequest;
import com.example.services.utility.WeatherForecastServiceRequest;

import java.io.IOException;
import java.util.HashMap;

public final class ServiceFactory {
    private ServiceFactory() {

    }

    /**
     * Employs the factory pattern to map a service name to its corresponding
     * service request object.
     *
     * @param serviceName The name of the service.
     * @param payload     The payload - data required to complete the API call.
     * @return The ServiceRequest object.
     */
    public static AbstractServiceRequest getServiceRequestByName(String serviceName, HashMap<String, String> payload) {
        switch (serviceName.toLowerCase()) {
            case "air quality":
                return new AirQualityServiceRequest(payload);
            case "book":
                return new BookSearchServiceRequest(payload);
            case "charity search":
                return new CharityBySearchServiceRequest(payload);
            case "charity by city":
                return new CharityByCityServiceRequest(payload);
            case "current weather":
                return new CurrentWeatherServiceRequest(payload);
            case "dictionary":
                return new DictionaryServiceRequest(payload);
            case "ingredient":
                return new RecipeByIngredientServiceRequest(payload);
            case "joke":
                return new JokeServiceRequest(payload);
            case "nearest transport":
                return new NearestBusOrTrainServiceRequest(payload);
            case "news":
                return new NewsServiceRequest(payload);
            case "random recipe":
                return new RandomRecipeServiceRequest(payload);
            case "recipe":
                return new RecipeBySearchServiceRequest(payload);
            case "recipe instructions":
                return new RecipeInstructionsService(payload);
            case "stocks":
                return new StocksServiceRequest(payload);
            case "transport search":
                return new BusOrTrainBySearchServiceRequest(payload);
            case "weather forecast":
                return new WeatherForecastServiceRequest(payload);
            default:
                try {
                    return SchemaServiceFactory.getService(serviceName, payload);
                } catch (IOException ignored) {
                    return null;
                }
        }
    }
}
