package net.medrag.microservices.misc.rest.config;

import net.medrag.microservices.misc.rest.model.Seniority;
import net.medrag.microservices.misc.rest.model.UserV2;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Stanislav Tretyakov
 * 03.12.2021
 */
@Component
public class ProtoMessageConverter extends AbstractHttpMessageConverter<UserV2> {

    public static final String VND_MEDRAG_PROTO = "application/vnd.medrag.proto";

    public ProtoMessageConverter() {
        super(new MediaType("application", "vnd.medrag.proto"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return UserV2.class.isAssignableFrom(clazz);
    }

    @Override
    protected UserV2 readInternal(Class<? extends UserV2> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        final InputStream inputStream = inputMessage.getBody();
        final String source = new String(inputStream.readAllBytes());
        final String[] split = source.split("\n");
        final String split1 = split[0].split(":")[1];
        final String split2 = split[1].split(":")[1];
        final String split3 = split[2].split(":")[1];
        final String split4 = split[3].split(":")[1];
        return new UserV2(split1, split2, split3, Seniority.valueOf(split4));
    }

    @Override
    protected void writeInternal(UserV2 userV2, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        try (OutputStream outputStream = outputMessage.getBody()) {
            final String body = String.format("name:%s\nsurname:%s\ncontact:%s\nseniority:%s",
                    userV2.getName(), userV2.getSurname(), userV2.getContact(), userV2.getSeniority());
            outputStream.write(body.getBytes());
        }
    }
}
