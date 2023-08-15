package by.system.news.repository;

import by.system.news.entity.News;
import by.system.news.entity.QNews;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;

@RepositoryRestResource
public interface NewsRepository extends
        JpaRepository<News, Long>,
        QuerydslPredicateExecutor<News>,
        QuerydslBinderCustomizer<QNews> {

    default void customize(@NonNull QuerydslBindings bindings,
                           @NonNull QNews qNews) {
        bindings.including(qNews.text, qNews.title);
        bindings.bind(qNews.title)
                .first(StringExpression::contains);
    }
}
