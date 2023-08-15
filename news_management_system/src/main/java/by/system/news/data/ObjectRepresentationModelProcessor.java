package by.system.news.data;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ObjectRepresentationModelProcessor implements RepresentationModelProcessor<EntityModel<Object>> {

    @NonNull
    @Override
    public EntityModel<Object> process(EntityModel<Object> model) {
        return EntityModel.of(Objects.requireNonNull(model.getContent()));
    }
}
