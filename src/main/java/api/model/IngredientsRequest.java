
package api.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class IngredientsRequest {

    private List<String> ingredients;
}