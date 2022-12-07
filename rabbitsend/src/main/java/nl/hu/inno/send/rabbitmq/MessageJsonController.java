package nl.hu.inno.send.rabbitmq;


import nl.hu.inno.send.TestObject;
import nl.hu.inno.send.TestObject2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MessageJsonController {

    private RabbitMQJsonProducer jsonProducer;

    public MessageJsonController(RabbitMQJsonProducer jsonProducer) {
        this.jsonProducer = jsonProducer;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> sendJsonMessage(@RequestBody TestObject testObject) {
        TestObject2 object = (TestObject2) jsonProducer.sendobject(testObject, TestObject2.class, "json");
        System.out.println(object);
        return ResponseEntity.ok("Json message sent to RabbitMQ ...");
    }
}
