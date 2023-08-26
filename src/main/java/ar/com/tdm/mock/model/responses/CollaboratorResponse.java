package ar.com.tdm.mock.model.responses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollaboratorResponse {
    private String lastSync;
    private List<String> editors;
    private List<String> writers;
    private List<String> colorists;
}