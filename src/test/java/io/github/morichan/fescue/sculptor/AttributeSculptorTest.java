package io.github.morichan.fescue.sculptor;

import io.github.morichan.fescue.feature.Attribute;
import io.github.morichan.fescue.feature.multiplicity.Bounder;
import io.github.morichan.fescue.feature.multiplicity.MultiplicityRange;
import io.github.morichan.fescue.feature.name.Name;
import io.github.morichan.fescue.feature.property.*;
import io.github.morichan.fescue.feature.type.Type;
import io.github.morichan.fescue.feature.value.DefaultValue;
import io.github.morichan.fescue.feature.value.expression.*;
import io.github.morichan.fescue.feature.visibility.Visibility;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import io.github.morichan.fescue.parser.ClassFeatureParser;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttributeSculptorTest {

    AttributeSculptor obj;

    @Nested
    class 正しい属性文の際 {

        @BeforeEach
        void setup() {
            obj = new AttributeSculptor();
        }

        @Test
        void 取得するコンテキストがPropertyContext型である() {
            String text = "- attribute";

            obj.parse(text);
            ParserRuleContext actual = obj.getContext();

            assertThat(actual).isInstanceOf(ClassFeatureParser.PropertyContext.class);
        }

        @Nested
        class 名前のみの場合 {

            @BeforeEach
            void setup() {
                obj = new AttributeSculptor();
            }

            @Test
            void 設定したインスタンスを返す() {
                Attribute expected = new Attribute(new Name("name"));

                obj.parse("name");
                Attribute actual = obj.carve();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class 名前と可視性の場合 {

            @BeforeEach
            void setup() {
                obj = new AttributeSculptor();
            }

            @Test
            void 設定したインスタンスを返す() {
                Attribute expected = new Attribute(new Name("name"));
                expected.setVisibility(Visibility.Private);

                obj.parse("- name");
                Attribute actual = obj.carve();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class 名前と派生の場合 {

            @BeforeEach
            void setup() {
                obj = new AttributeSculptor();
            }

            @Test
            void 設定したインスタンスを返す() {
                Attribute expected = new Attribute(new Name("name"));
                expected.setDerived(true);

                obj.parse("/name");
                Attribute actual = obj.carve();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class 名前と型の場合 {

            @BeforeEach
            void setup() {
                obj = new AttributeSculptor();
            }

            @Test
            void 設定したインスタンスを返す() {
                Attribute expected = new Attribute(new Name("number"));
                expected.setType(new Type("int"));

                obj.parse("number : int");
                Attribute actual = obj.carve();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class 名前と多重度の場合 {

            @BeforeEach
            void setup() {
                obj = new AttributeSculptor();
            }

            @Test
            void 上限のみを設定したインスタンスを返す() {
                Attribute expected = new Attribute(new Name("number"));
                expected.setMultiplicityRange(new MultiplicityRange(new Bounder(new OneIdentifier("*"))));

                obj.parse("number [*]");
                Attribute actual = obj.carve();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }

            @Test
            void 上限および下限を設定したインスタンスを返す() {
                Attribute expected = new Attribute(new Name("number"));
                expected.setMultiplicityRange(new MultiplicityRange(new Bounder(new OneIdentifier(0)), new Bounder(new OneIdentifier(1))));

                obj.parse("number [0..1]");
                Attribute actual = obj.carve();

                assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
            }
        }

        @Nested
        class 名前と既定値において {

            @Nested
            class 項が1つの場合 {

                @BeforeEach
                void setup() {
                    obj = new AttributeSculptor();
                }

                @Test
                void デフォルト数値のインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(new OneIdentifier(0)));

                    obj.parse("number = 0");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void プラス数値のインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(new Monomial("+", new OneIdentifier(1))));

                    obj.parse("number = +1");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 否定式のインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(new Monomial("not", new OneIdentifier("true"))));

                    obj.parse("number = not true");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }
            }

            @Nested
            class 項が2つの場合 {

                @BeforeEach
                void setup() {
                    obj = new AttributeSculptor();
                }

                @Test
                void 加算のインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("newNumber"));
                    expected.setDefaultValue(new DefaultValue(
                            new Binomial("+", new OneIdentifier("number"), new OneIdentifier(1))));

                    obj.parse("newNumber = number + 1");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 大なりイコールのインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("newNumber"));
                    expected.setDefaultValue(new DefaultValue(
                            new Binomial(">=", new OneIdentifier("number"), new OneIdentifier(1))));

                    obj.parse("newNumber = number >= 1");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Nested
                class ドットのインスタンスでは {

                    @BeforeEach
                    void setup() {
                        obj = new AttributeSculptor();
                    }

                    @Test
                    void インスタンスのインスタンスを返す() {
                        Attribute expected = new Attribute(new Name("newNumber"));
                        expected.setDefaultValue(new DefaultValue(
                                new Binomial(".", new OneIdentifier("instances"), new OneIdentifier("instance"))));

                        obj.parse("newNumber = instances.instance");
                        Attribute actual = obj.carve();

                        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                    }

                    @Test
                    void インスタンスの引数のないメソッドを返す() {
                        Attribute expected = new Attribute(new Name("newNumber"));
                        expected.setDefaultValue(new DefaultValue(new Binomial(".",
                                new OneIdentifier("instances"), new MethodCall("method"))));

                        obj.parse("newNumber = instances.method()");
                        Attribute actual = obj.carve();

                        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                    }

                    @Test
                    void インスタンスの引数のあるメソッドを返す() {
                        Attribute expected = new Attribute(new Name("newNumber"));
                        expected.setDefaultValue(new DefaultValue(new Binomial(".",
                                new OneIdentifier("instances"), new MethodCall("method", new OneIdentifier("arg")))));

                        obj.parse("newNumber = instances.method(arg)");
                        Attribute actual = obj.carve();

                        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                    }

                    @Test
                    void インスタンスの引数のあるメソッドのインスタンスを返す() {
                        Attribute expected = new Attribute(new Name("newNumber"));
                        expected.setDefaultValue(new DefaultValue(new Binomial(".",
                                new Binomial(".",
                                        new OneIdentifier("instances"),
                                        new MethodCall("methods", new OneIdentifier("withArg"))),
                                new OneIdentifier("instance"))));

                        obj.parse("newNumber = instances.methods(withArg).instance");
                        Attribute actual = obj.carve();

                        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                    }

                    @Test
                    void インスタンスの引数のあるメソッドの引数のあるメソッドを返す() {
                        Attribute expected = new Attribute(new Name("newNumber"));
                        expected.setDefaultValue(new DefaultValue(new Binomial(".",
                                new Binomial(".",
                                        new OneIdentifier("instances"),
                                        new MethodCall("methods", new MethodCall("withMethod"))),
                                new MethodCall("method", new OneIdentifier("with"), new OneIdentifier("arg")))));

                        obj.parse("newNumber = instances.methods(withMethod()).method(with, arg)");
                        Attribute actual = obj.carve();

                        assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                    }
                }
            }

            @Nested
            class メソッドの場合 {

                @BeforeEach
                void setup() {
                    obj = new AttributeSculptor();
                }

                @Test
                void 引数のないメソッドを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(
                            new MethodCall("methodName")));

                    obj.parse("number = methodName()");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 引数が1つのメソッドを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(
                            new MethodCall("methodName", new OneIdentifier(1))));

                    obj.parse("number = methodName(1)");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 引数が3つのメソッドを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(
                            new MethodCall("methodName", Arrays.asList(
                                    new OneIdentifier(0),
                                    new OneIdentifier("instance"),
                                    new Monomial("-", new OneIdentifier(1))))));

                    obj.parse("number = methodName(0, instance, -1)");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 引数のないメソッドのインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(new Binomial(".",
                            new MethodCall("methods"),
                            new OneIdentifier("instance"))));

                    obj.parse("number = methods().instance");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 引数のあるメソッドの引数のあるメソッドを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(new Binomial(".",
                            new MethodCall("methods", new OneIdentifier("with"), new OneIdentifier("arg")),
                            new MethodCall("method", new OneIdentifier(1), new OneIdentifier(2), new OneIdentifier(3)))));

                    obj.parse("number = methods(with, arg).method(1, 2, 3)");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 引数のないメソッドの引数のあるメソッドのインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(new Binomial(".",
                            new Binomial(".",
                                    new MethodCall("methods"),
                                    new MethodCall("method", new OneIdentifier(1), new OneIdentifier(2), new OneIdentifier(3))),
                            new OneIdentifier("inInstance"))));

                    obj.parse("number = methods().method(1, 2, 3).inInstance");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 引数のあるメソッドの引数のあるメソッドの引数のあるメソッドの引数のあるメソッドを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(new Binomial(".",
                            new Binomial(".",
                                    new Binomial(".",
                                            new MethodCall("method", new OneIdentifier("arg1")),
                                            new MethodCall("forMethod", new OneIdentifier("arg2"))),
                                    new MethodCall("ofMethod", new OneIdentifier("arg3"))),
                            new MethodCall("inMethod", new OneIdentifier("arg4")))));

                    obj.parse("number = method(arg1).forMethod(arg2).ofMethod(arg3).inMethod(arg4)");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }
            }

            @Nested
            class カッコで囲っている式の場合 {

                @BeforeEach
                void setup() {
                    obj = new AttributeSculptor();
                }

                @Test
                void 項が1つのインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(
                            new ExpressionWithParen(new OneIdentifier("textAroundParen"))));

                    obj.parse("number = (textAroundParen)");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }

                @Test
                void 項が4つのインスタンスを返す() {
                    Attribute expected = new Attribute(new Name("number"));
                    expected.setDefaultValue(new DefaultValue(
                            new Binomial("/",
                                    new Binomial("*",
                                            new ExpressionWithParen(
                                                    new Binomial("+",
                                                            new OneIdentifier("upperBase"),
                                                            new OneIdentifier("lowerBase"))),
                                            new OneIdentifier("height")),
                                    new OneIdentifier(2))));

                    obj.parse("number = (upperBase + lowerBase) * height / 2");
                    Attribute actual = obj.carve();

                    assertThat(actual).isEqualToComparingFieldByFieldRecursively(expected);
                }
            }
        }

        @Nested
        class 名前とプロパティの場合 {

            @BeforeEach
            void setup() {
                obj = new AttributeSculptor();
            }

            @Test
            void プロパティが1つのインスタンスを返す() {
                Attribute expected = new Attribute(new Name("number"));
                expected.addProperty(new ReadOnly());

                obj.parse("number {readOnly}");
                Attribute actual = obj.carve();

                assertThat(actual).hasToString(expected.toString());
            }

            @Test
            void 複雑なプロパティが1つのインスタンスを返す() {
                Attribute expected = new Attribute(new Name("number"));
                expected.addProperty(new Redefines(new OneIdentifier("redefinedNumber")));

                obj.parse("number {redefines redefinedNumber}");
                Attribute actual = obj.carve();

                assertThat(actual).hasToString(expected.toString());
            }

            @Test
            void プロパティが6つのインスタンスを返す() {
                Attribute expected = new Attribute(new Name("number"));
                expected.addProperty(new ReadOnly());
                expected.addProperty(new Union());
                expected.addProperty(new Subsets(new MethodCall("getNumber")));
                expected.addProperty(new Redefines(new Binomial("+", new OneIdentifier("number"), new OneIdentifier(1))));
                expected.addProperty(new Ordered());
                expected.addProperty(new Unique());

                obj.parse("number {readOnly, union, subsets getNumber(), redefines number + 1, ordered, unique}");
                Attribute actual = obj.carve();

                assertThat(actual).hasToString(expected.toString());
            }
        }
    }

    @Nested
    class 不正な属性文の際 {

        @BeforeEach
        void setup() {
            obj = new AttributeSculptor();
        }

        @Test
        void 空文字をパースしようとすると例外を投げる() {
            assertThatThrownBy(() -> obj.parse("")).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void nullをパースしようとすると例外を投げる() {
            assertThatThrownBy(() -> obj.parse(null)).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void パースする前にコンテキストを取得しようとすると例外を投げる() {
            assertThatThrownBy(() -> obj.getContext()).isInstanceOf(IllegalStateException.class);
        }
    }
}