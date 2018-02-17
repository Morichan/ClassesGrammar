package feature.value.expression.symbol;

/**
 * <p> 乗算クラス </p>
 *
 * <p>
 *     演算子における乗算{@code "*"}クラスです。
 * </p>
 */
public class Multi extends Symbol {

    /**
     * <p> 演算子の両端にスペースを保持している場合は真を返す真偽値判定を行う。 </p>
     *
     * @return 真
     */
    @Override
    public boolean isHadSpaceBothSides() {
        return true;
    }

    /**
     * <p> 乗算演算子の文字列を取得します。 </p>
     *
     * @return {@code "*"}
     */
    @Override
    public String toString() {
        return "*";
    }
}
