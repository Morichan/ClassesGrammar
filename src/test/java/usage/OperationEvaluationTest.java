package usage;

import org.antlr.v4.runtime.InputMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class OperationEvaluationTest {

    OperationEvaluation obj;

    @Nested
    class 正しい文法を入力する際に {

        @Nested
        class 文を入力していない場合 {

            @BeforeEach
            void setup() {
                obj = new OperationEvaluation();
            }

            @Test
            void 文を設定すると文を返す() {
                String expected = "+ operation()";

                obj.setText(expected);
                String actual = obj.getText();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 文を設定して走査するとエラーを返さない() {
                String expected = "+ operation()";

                obj.setText(expected);
                obj.walk();
                String actual = obj.getText();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 名前だけの場合 {
            final String operation = "operation()";

            @BeforeEach
            void setup() {
                walk(operation);
            }

            @Test
            void 属性を返す() {
                String expected = "operation";

                String actual = obj.extractName();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 可視性を含む場合 {
            final String operation = "+ operation()";

            @BeforeEach
            void setup() {
                walk(operation);
            }

            @Test
            void 属性を返す() {
                String expected = "operation";

                String actual = obj.extractName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 可視性を返す() {
                String expected = "+";

                String actual = obj.extractVisibility();

                assertThat(actual).isEqualTo(expected);
            }
        }

        @Nested
        class 型を含む場合 {
            final String operation = "operation() : int";

            @BeforeEach
            void setup() {
                walk(operation);
            }

            @Test
            void 名前を返す() {
                String expected = "operation";

                String actual = obj.extractName();

                assertThat(actual).isEqualTo(expected);
            }

            @Test
            void 型を返す() {
                String expected = "int";

                String actual = obj.extractReturnType();

                assertThat(actual).isEqualTo(expected);
            }
        }
    }

    @Nested
    class 正しくない文法を入力する際に {

        @Nested
        class 文を入力していない場合 {

            @BeforeEach
            void setup() {
                obj = new OperationEvaluation();
            }

            @Test
            void 文を設定せずに取得しようとするとnullを返す() {

                String actual = obj.getText();

                assertThat(actual).isNull();
            }

            @Test
            void 走査したらエラーを返す() {
                obj = new OperationEvaluation();

                assertThatThrownBy(() -> obj.walk()).isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        class 名前の場合 {

            @Test
            void 空文字を入力するとエラーを返す() {
                walk("");

                assertThatThrownBy(() -> obj.extractName()).isInstanceOf(IllegalArgumentException.class);
            }

            @Test
            void 予約語と同じ文字列を入力するとエラーを返す() {
                walk("not : float");

                assertThatThrownBy(() -> obj.extractName()).isInstanceOf(InputMismatchException.class);
            }
        }
    }

    private void walk(String text) {
        obj = new OperationEvaluation();
        obj.setText(text);
        obj.walk();
    }
}