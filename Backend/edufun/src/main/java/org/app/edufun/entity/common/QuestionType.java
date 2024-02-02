package org.app.edufun.entity.common;

import java.util.Set;

public enum QuestionType {
    PUZZLE, GENERAL_KNOWLEDGE, MATHS, APTITUDE, SCIENCE, TECHNICAL, OTHERS;

    private static final Set<QuestionType> VALUES = Set.of(QuestionType.values());
    public static Set<QuestionType> getValues() {
        return VALUES;
    }
}
