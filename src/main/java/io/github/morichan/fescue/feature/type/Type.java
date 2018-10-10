package io.github.morichan.fescue.feature.type;

import io.github.morichan.fescue.feature.name.Name;

import java.util.Arrays;
import java.util.List;

/**
 * <p> 型クラス </p>
 *
 * <p>
 *     使い方を次に示します。
 * </p>
 *
 * <pre>
 *     {@code
 *     Type type = new Type("int");
 *
 *     System.out.println(type); // "int"
 *     }
 * </pre>
 */
public class Type {
    /**
     * 型の名前
     */
    private Name name;

    /**
     * 定義済みの型の名前のリスト
     */
    private static final List<String> predefinedTypeNames = Arrays.asList(
            "bool", "boolean",
            "c", "char", "character",
            "i8", "int8", "int8_t", "byte",
            "i16", "int16", "int16_t", "short",
            "i32", "int32", "int32_t", "int", "integer",
            "i64", "int64", "int64_t", "long",
            "f32", "float",
            "lf", "f64", "double",
            "void");

    /**
     * <p> デフォルトコンストラクタ </p>
     *
     * <p>
     *     @deprecated
     *     現在は使ってもいいですが、{@link #setName(Name)}を使わなければならないという実装上の手間がかかるため、推奨しません。
     *     {@link #Type(String)}をご利用ください。
     * </p>
     */
    @Deprecated
    public Type() {}

    /**
     * <p> 型設定コンストラクタ </p>
     *
     * <p>
     *     型の名前の文字列を最初に設定します。
     *     設定時に{@code null}判定と空文字判定を行い、真の場合は{@link IllegalArgumentException}を投げます（{@link Name#Name(String)}および{@link #setName(Name)}参照）。
     * </p>
     *
     * @param text 型の名前の文字列<br>{@code null}および{@code ""}（空文字）不可
     */
    public Type(String text) {
        Name name = new Name(text);
        setName(name);
    }

    /**
     * <p> 型の名前を設定します。 </p>
     *
     * <p>
     *     型の名前を最初に設定します。
     * </p>
     *
     * @param name 型の名前<br>{@code null}不可
     */
    public void setName(Name name) {
        // checkTypeName(name);
        this.name = name;
    }

    /**
     * <p> 型の名前を取得します。 </p>
     *
     * <p>
     *     取得時に{@code null}判定を行います。
     *     名前が設定されていない場合（{@link #setName(Name)}または{@link #Type(String)}未実行の場合）は{@link IllegalStateException}を投げます。
     * </p>
     *
     * @return 型の名前<br>{@code null}および{@code ""}（空文字）無し
     */
    public Name getName() {
        if (name == null) throw new IllegalStateException();
        return name;
    }

    /**
     * <p> 型の名前の文字列を取得します。 </p>
     *
     * <p>
     *     {@link #getName()}を呼び出しています。
     * </p>
     *
     * @return 型の名前の文字列<br>{@code null}および{@code ""}（空文字）無し（{@link #getName()}参照）
     */
    @Override
    public String toString() {
        return getName().toString();
    }

    /**
     * <p> 入力した名前が{@code null}または定義済みの型でない場合は{@link IllegalArgumentException}を投げます。 </p>
     *
     * <p>
     *     定義済みの型は{@link #predefinedTypeNames}で定義しています。
     * </p>
     *
     * @param name 任意の名前
     */
    private void checkTypeName(Name name) {
        if (name == null || ! predefinedTypeNames.contains(name.getNameText())) throw new IllegalArgumentException();
    }
}
