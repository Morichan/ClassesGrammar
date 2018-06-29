package io.github.morichan.fescue.evaluation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import io.github.morichan.fescue.parser.ClassFeatureParser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OperationEvaluationTest {

    OperationEvaluation obj;

    @Nested
    class 正しい文を入力している場合 {

        @BeforeEach
        void setup() {
            obj = new OperationEvaluation();
        }

        @Test
        void 文を設定すると文を返す() {
            String expected = "operation()";

            obj.setText(expected);
            String actual = obj.getText();

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 文を設定して走査するとエラーを返さない() {
            String expected = "operation()";
            String actual;

            obj.setText("operation()");
            obj.walk();
            actual = obj.getText();

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        void 文を設定して走査するとコンテキストを返す() {
            ClassFeatureParser.OperationContext actual;

            obj.setText("+ operation()");
            obj.walk();
            actual = obj.getContext();

            assertThat(actual).isInstanceOf(ClassFeatureParser.OperationContext.class);
        }
    }

    @Nested
    class 正しい文を入力していない場合 {

        @BeforeEach
        void setup() {
            obj = new OperationEvaluation();
        }

        @Test
        void 文を設定せずに走査したらエラーを返す() {
            assertThatThrownBy(() -> obj.walk()).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 文を設定せずに文を取得しようとしたら例外を投げる() {
            assertThatThrownBy(() -> obj.getText()).isInstanceOf(IllegalStateException.class);
        }

        @Test
        void 文を走査せずにコンテキストを取得しようとしたら例外を投げる() {

            obj.setText("operation()");

            assertThatThrownBy(() -> obj.getContext()).isInstanceOf(IllegalStateException.class);
        }
    }
}