package usage;

import org.antlr.v4.runtime.InputMismatchException;
import parser.ClassesParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract public class ClassEvaluation implements Evaluation {

    private boolean isSameBetweenNameAndKeyword = false;
    private InputMismatchException inputMismatchException;

    /**
     * 名前が予約語と同じ文字列かどうかを判定し、同じ場合は{@link ClassesParser.PropertyContext#exception}を返します。
     */
    protected void checkIfNameIsSamePrimitiveType() {
        if (isSameBetweenNameAndKeyword) throw inputMismatchException;
    }

    /**
     * <p> {@link #checkIfNameIsSamePrimitiveType()}で必要な変数2つ（{@link #isSameBetweenNameAndKeyword}と{@link #inputMismatchException}）を初期化します。 </p>
     *
     * <p>
     *     このメソッドは{@link #walk()}の最初に使ってください。
     * </p>
     */
    protected void initIfIsSameBetweenNameAndKeyword() {
        inputMismatchException = null;
        isSameBetweenNameAndKeyword = false;
    }

    /**
     * <p> 例外インスタンスを設定します。 </p>
     *
     * <p>
     *     {@code try-catch}文で利用してください。
     * </p>
     *
     * @param e {@code try-catch}文でキャッチした{@link InputMismatchException}インスタンス
     */
    protected void setInputMismatchException(InputMismatchException e) {
        inputMismatchException = e;
        isSameBetweenNameAndKeyword = true;
    }

    /**
     * <p> {@code "\\.\\."}という文字列（2つ連続したドット）が存在した場合、その両端に半角スペースを挿入します。 </p>
     *
     * <p>
     *     正確には、文法における多重度の下限と上限の指定（[0..1]のようなもの）内での、真ん中の2つのドット（以降は範囲演算子と呼びます）の両端に半角スペースを挿入します。
     *     これは、範囲演算子の箇所をパースする際に、半角スペースが入っていない場合はうまくパースできないためです。
     *     手法としては、単純な置換（{@code matcher("\\.\\.") -> replaceAll(" .. ")}）を用いています。
     *     既に半角スペースが入っていたとしても、新たに半角スペースを挿入しますが、パースに問題はありません。
     * </p>
     *
     * @param text 設定する属性文 {@code null}可だが{@link #walk()}で{@link IllegalArgumentException}を投げる。
     * @return もし {@code "\\.\\."}が存在する場合はその両端に半角スペースを挿入した文字列、存在しない場合は入力した文字列
     */
    protected String insertSpaceBothEndsOfRangeOperator(String text) {
        Pattern p = Pattern.compile("\\.\\.");
        Matcher m = p.matcher(text);
        if (m.find()) text = m.replaceAll(" .. ");
        return text;
    }
}
