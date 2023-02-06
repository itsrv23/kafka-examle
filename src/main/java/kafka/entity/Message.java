package kafka.entity;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String message;
}
