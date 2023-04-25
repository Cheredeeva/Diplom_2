package api.order;

import java.util.List;

public class CreateOrderRequestBody {
    public List<String> ingredients;

    public CreateOrderRequestBody(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
