import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FineTicket {
    private Date date_time;
    private String first_name;
    private String last_name;
    private String type;
    private double fine_amount;
}
