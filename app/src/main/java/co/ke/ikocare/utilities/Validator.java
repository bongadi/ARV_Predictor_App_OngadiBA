package co.ke.ikocare.utilities;

import android.util.Patterns;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    public static final int REQUEST_PERMISSIONS = 100;

    public static boolean isEmpty(EditText editText){
        return editText.getText().toString().equals("");
    }

    public static boolean isEmail(EditText editText){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(editText.getText().toString()).matches();
    }

    public static boolean isOfLength(EditText editText, int length){
        return editText.getText().toString().length() >= length;
    }

    public static boolean isOfFixedLength(EditText editText, int length){
        return editText.getText().toString().length() == length;
    }

    public static boolean isPhone(EditText editText, int length, String startwith){
        return editText.getText().toString().startsWith(startwith) && editText.getText().toString().length() == length;
    }

    public static boolean isEqual(EditText editText, EditText editText2){
        return editText.getText().toString().equals(editText2.getText().toString());
    }

    public static boolean isCheckboxChecked(CheckBox checkBox){
        return checkBox.isChecked();
    }

    public static boolean isSelectionValid(Spinner spinner){
        return spinner.getSelectedItemId() == 0;
    }

    public static String stringlistjoiner(List<String> list, String separator){
        StringBuilder result = new StringBuilder();
        for(String term : list) result.append(term).append(separator);
        return result.deleteCharAt(result.length()-1).toString();
    }
}
