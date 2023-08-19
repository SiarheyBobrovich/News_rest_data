package by.system.news.data;

import by.system.news.entity.Comment;

public record Log(String type,
                  Comment comment) {
}
