package feature.parameter;

import feature.direction.Direction;
import feature.direction.In;
import feature.multiplicity.MultiplicityRange;
import feature.name.Name;
import feature.property.Property;
import feature.type.Type;
import feature.value.DefaultValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringJoiner;

/**
 * <p> パラメータクラス </p>
 *
 * <p>
 *     使い方を次に示します。
 * </p>
 *
 * <pre>
 *     {@code
 *     Parameter parameter = new Parameter();
 *
 *     parameter.setName(new Name("parameterName"));
 *     parameter.setType(new Type("void"));
 *
 *     // ...
 *
 *     Name parameterName = attribute.getName();
 *     Type parameterType = attribute.getType();
 *
 *     System.out.println(parameterName); // "parameterName"
 *     System.out.println(parameterType); // "void"
 *     }
 * </pre>
 */
public class Parameter {
    private Direction direction;
    private Name parameterName;
    private Type parameterType;
    private MultiplicityRange multiplicityRange;
    private DefaultValue value;
    private List<Property> properties = new ArrayList<>();

    /**
     * <p> プロパティ名設定コンストラクタ </p>
     *
     * <p>
     *     プロパティ名を最初に設定します。
     *     設定時に{@code null}判定と空文字判定を行い、真の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     *
     * <p>
     *     同時に、方向も初期化します。
     *     初期値は{@link Direction#isOuted()}が偽である{@link feature.direction.In}インスタンスです。
     * </p>
     *
     * @param name プロパティ名<br>{@code null}および{@code ""}（空文字）不可
     */
    public Parameter(Name name) {
        checkIllegalArgument(name);
        parameterName = name;
        direction = new In();
    }

    /**
     * <p> 方向を設定します。 </p>
     *
     * <p>
     *     設定する前に{@code null}判定を行い、{@code null}の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     *
     * @param direction 方向<br>{@code null}不可
     */
    public void setDirection(Direction direction) {
        checkIllegalArgument(direction);
        this.direction = direction;
    }

    /**
     * <p> 方向を取得します。 </p>
     *
     * <p>
     *     取得する前に、フィールドとして方向インスタンスを保持しているかどうかを判定します。
     *     保持していない場合は{@link IllegalStateException}を投げます（{@link #checkIllegalState(Object)}参照）。
     * </p>
     *
     * @return 方向<br>{@code null}なし
     * @throws IllegalStateException 方向が存在しないことを示す要素不所持例外
     */
    public Direction getDirection() throws IllegalStateException {
        checkIllegalState(direction);
        return direction;
    }

    /**
     * <p> パラメータ名を設定します。 </p>
     *
     * <p>
     *     設定する前に{@code null}判定を行い、{@code null}の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     *
     * @param parameterName パラメータ名<br>{@code null}不可
     */
    public void setName(Name parameterName) {
        checkIllegalArgument(parameterName);
        this.parameterName = parameterName;
    }

    /**
     * <p> パラメータ名を取得します。 </p>
     *
     * <p>
     *     取得する前に、フィールドとしてパラメータ名インスタンスを保持しているかどうかを判定します。
     *     保持していない場合は{@link IllegalStateException}を投げます（{@link #checkIllegalState(Object)}参照）。
     * </p>
     *
     * @return 名前<br>{@code null}なし
     * @throws IllegalStateException パラメータ名が存在しないことを示す要素不所持例外
     */
    public Name getName() throws IllegalStateException {
        checkIllegalState(parameterName);
        return parameterName;
    }

    /**
     * <p> パラメータの型を設定します。 </p>
     *
     * <p>
     *     設定する前に{@code null}判定を行い、{@code null}の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     *
     * @param parameterType パラメータの型<br>{@code null}不可
     */
    public void setType(Type parameterType) {
        checkIllegalArgument(parameterType);
        this.parameterType = parameterType;
    }

    /**
     * <p> パラメータの型を取得します。 </p>
     *
     * <p>
     *     取得する前に、フィールドとしてパラメータの型インスタンスを保持しているかどうかを判定します。
     *     保持していない場合は{@link IllegalStateException}を投げます（{@link #checkIllegalState(Object)}参照）。
     * </p>
     *
     * @return パラメータの型<br>{@code null}なし
     * @throws IllegalStateException パラメータの型が存在しないことを示す要素不所持例外
     */
    public Type getType() throws IllegalStateException {
        checkIllegalState(parameterType);
        return parameterType;
    }

    /**
     * <p> 多重度を設定します。 </p>
     *
     * <p>
     *     設定する前に{@code null}判定を行い、{@code null}の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     *
     * @param multiplicityRange 多重度<br>{@code null}不可
     */
    public void setMultiplicityRange(MultiplicityRange multiplicityRange) {
        checkIllegalArgument(multiplicityRange);
        this.multiplicityRange = multiplicityRange;
    }

    /**
     * <p> 多重度を取得します。 </p>
     *
     * <p>
     *     取得する前に、フィールドとして多重度インスタンスを保持しているかどうかを判定します。
     *     保持していない場合は{@link IllegalStateException}を投げます（{@link #checkIllegalState(Object)}参照）。
     * </p>
     *
     * @return 多重度<br>{@code null}なし
     * @throws IllegalStateException 多重度が存在しないことを示す要素不所持例外
     */
    public MultiplicityRange getMultiplicityRange() throws IllegalStateException {
        checkIllegalState(multiplicityRange);
        return multiplicityRange;
    }

    /**
     * <p> 既定値を設定します。 </p>
     *
     * <p>
     *     設定する前に{@code null}判定を行い、{@code null}の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     *
     * @param defaultValue 既定値<br>{@code null}不可
     */
    public void setDefaultValue(DefaultValue defaultValue) {
        checkIllegalArgument(defaultValue);
        this.value = defaultValue;
    }

    /**
     * <p> 既定値を取得します。 </p>
     *
     * <p>
     *     取得する前に、フィールドとして既定値インスタンスを保持しているかどうかを判定します。
     *     保持していない場合は{@link IllegalStateException}を投げます（{@link #checkIllegalState(Object)}参照）。
     * </p>
     *
     * @return 既定値<br>{@code null}なし
     * @throws IllegalStateException 既定値が存在しないことを示す要素不所持例外
     */
    public DefaultValue getDefaultValue() throws IllegalStateException {
        checkIllegalState(value);
        return value;
    }

    /**
     * <p> プロパティを追加します。 </p>
     *
     * <p>
     *     追加する前に{@code null}判定を行い、{@code null}の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     * @param property プロパティ<br>{@code null}不可
     */
    public void addProperty(Property property) {
        checkIllegalArgument(property);
        properties.add(property);
    }

    /**
     * <p> 任意のプロパティを取得します。 </p>
     *
     * <p>
     *     取得する前に、設定値のインデックスをリストとして保持しているかどうかを判定します。
     *     保持していない場合は{@link IllegalStateException}を投げます（{@link #checkIllegalState(Object)}参照）。
     * </p>
     *
     * @param index プロパティリストのインデックス
     * @return プロパティ<br>{@code null}なし
     * @throws IllegalStateException プロパティが存在しないことを示す要素不所持例外
     */
    public Property getProperty(int index) throws IllegalStateException {
        try {
            return properties.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException();
        }
    }

    /**
     * <p> プロパティのリストを設定します。 </p>
     *
     * <p>
     *     設定する前に{@code null}判定を行い、{@code null}の場合は{@link IllegalArgumentException}を投げます（{@link #checkIllegalArgument(Object)}参照）。
     * </p>
     *
     * @param properties プロパティのリスト<br>{@code null}不可
     */
    public void setProperties(List<Property> properties) {
        checkIllegalArgument(properties);
        this.properties = properties;
    }

    /**
     * <p> プロパティのリストを取得します。 </p>
     *
     * <p>
     *     取得する前に、プロパティのリストの要素数として1つ以上保持しているかどうかを判定します。
     *     保持していない場合は{@link IllegalStateException}を投げます（{@link #checkIllegalState(Object)}参照）。
     * </p>
     *
     * @return プロパティのリスト<br>{@code null}なし
     * @throws IllegalStateException プロパティのリストが存在しないことを示す要素不所持例外
     */
    public List<Property> getProperties() throws IllegalStateException {
        if (properties.size() == 0) throw new IllegalStateException();
        return properties;
    }

    /**
     * <p> プロパティの文字列を取得します。 </p>
     *
     * <p>
     *     文字列はUML2.0仕様書に準拠します。
     * </p>
     *
     * @return プロパティの文字列<br>{@code null}および{@code ""}なし
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (direction.isOuted()) {
            sb.append(direction);
            sb.append(" ");
        }

        sb.append(parameterName);

        if (parameterType != null) {
            sb.append(" : ");
            sb.append(parameterType);
        }

        if (multiplicityRange != null) {
            sb.append(" [");
            sb.append(multiplicityRange);
            sb.append("]");
        }

        if (value != null) {
            sb.append(" = ");
            sb.append(value);
        }

        if (properties.size() > 0) {
            StringJoiner sj = new StringJoiner(", ");
            for (Property prop : properties) sj.add(prop.toString());

            sb.append(" {");
            sb.append(sj);
            sb.append("}");
        }

        return sb.toString();
    }

    /**
     * <p> 外部から入力するオブジェクトの{@code null}判定を行います。 </p>
     *
     * <p>
     *     {@code null}の場合は{@link IllegalArgumentException}を投げます。
     * </p>
     *
     * @param object {@code null}判定を行いたいオブジェクト
     */
    private void checkIllegalArgument(Object object) {
        if (object == null) throw new IllegalArgumentException();
    }

    /**
     * <p> フィールドのオブジェクトを外部に出力する際に{@code null}判定を行います。 </p>
     *
     * <p>
     *     {@code null}の場合は{@link IllegalStateException}を投げます。
     * </p>
     *
     * @param object {@code null}判定を行いたいオブジェクト
     * @throws IllegalStateException 要素が存在しないことを示す要素不所持例外
     */
    private void checkIllegalState(Object object) throws IllegalStateException {
        if (object == null) throw new IllegalStateException();
    }
}
