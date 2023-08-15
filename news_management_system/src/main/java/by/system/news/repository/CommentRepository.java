package by.system.news.repository;

import by.system.news.entity.Comment;
import by.system.news.entity.QComment;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RepositoryRestResource
public interface CommentRepository extends
        JpaRepository<Comment, Long>,
        QuerydslPredicateExecutor<Comment>,
        QuerydslBinderCustomizer<QComment> {

    default void customize(@NonNull QuerydslBindings bindings,
                           @NonNull QComment qComment) {
//        bindings.bind(qComment.text)
//                .first(StringExpression::contains);
//        bindings.bind(qComment.username)
//                .first(StringExpression::contains);
//        bindings.bind(qComment.news.id)
//                .first(SimpleExpression::eq);

        AtomicReference<BooleanExpression> booleanExpression = new AtomicReference<>();

        bindings.bind(qComment.text, qComment.username)
                .all((path, value) -> {
                    value.forEach(x -> {
                        BooleanExpression expression = path.contains(x);
                        if (booleanExpression.get() == null) {
                            booleanExpression.set(expression);
                        } else {
                            booleanExpression.accumulateAndGet(expression, BooleanExpression::or);
                        }
                    });
                    return Optional.of(booleanExpression.get());
                });
    }
}
