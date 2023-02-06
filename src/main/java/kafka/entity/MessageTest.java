package kafka.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class MessageTest implements Serializable {

    private final Long id;
    private final String message;
}
