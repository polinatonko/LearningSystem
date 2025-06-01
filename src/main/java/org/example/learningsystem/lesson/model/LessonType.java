package org.example.learningsystem.lesson.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LessonType {

    VIDEO(Fields.VIDEO),

    CLASSROOM(Fields.CLASSROOM);

    private final String name;

    @Override
    public String toString() {
        return name;
    }

    public static final class Fields {

        public static final String VIDEO = "VIDEO";

        public static final String CLASSROOM = "CLASSROOM";
    }
}
