package myapp.releaseActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.budiyev.android.codescanner.*;
import com.google.zxing.Result;

import net.simplifiedcoding.simplifiedcoding.R;
import net.simplifiedcoding.simplifiedcoding.RequestHandler;
import net.simplifiedcoding.simplifiedcoding.SharedPrefManager;

import myapp.Employee;
import myapp.EmployeeInfoActivity;
import myapp.ProductInfoActivity;
import myapp.ScanActivity;
import myapp.api.CallbackImpl;
import myapp.api.MyApi;
import myapp.api.ResponseContainer;
import myapp.api.URLs;
import myapp.model.Product;
import retrofit2.Call;
import retrofit2.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashMap;
import java.util.List;

public class ScanToAddActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    private Button buttonCheck, buttonLogout2;
    //final String[] productsymbol_array = new String[1];
    public String symbol; // = productsymbol_array[0]; Prod, Empl

    private final MyApi apiService;
    private final String GET_PRODUCT_TAG = "GET_PRODUCT";
    private Product productScanned;

    public ScanToAddActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        buttonCheck = (Button) findViewById(R.id.buttonCheck);
        buttonLogout2 = (Button) findViewById(R.id.buttonLogout2);
        mCodeScanner = new CodeScanner(this, scannerView);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(ScanToAddActivity.this, result.getText(), Toast.LENGTH_SHORT); //.show();
                        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 330);
                        toast.show();
                        symbol = result.getText();
                    }
                });
            }
        });

        mCodeScanner.startPreview();

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });

        findViewById(R.id.buttonCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                //ScanActivity.getInstance(getApplicationContext()).logout();
                employeeLogin();
                productLogin();
            }

        });

        findViewById(R.id.buttonLogout2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }

    public void getProduct () {
        Call<ResponseContainer<Product>> callProducts = apiService.checkProduct();
        callProducts.enqueue(new CallbackImpl<ResponseContainer<Product>>(GET_PRODUCT_TAG){
            @Override
            public void onResponse(Call<ResponseContainer<Product>> call,
                                   Response<ResponseContainer<Product>> response) {
                super.onResponse(call, response);
                ResponseContainer<Product> resCon = response.body();
                Log.d(getTag(), resCon.getMessage());

                productScanned = resCon.getObject();
                Product prod;
                String strProd = prod.getSymbol()+ "  " + prod.getName() + "  " +
                        String.valueOf(prod.getQuantity());

            /*    checkedProducts = new boolean[productList.size()];
                for(int i=0; i < productList.size(); i++){
                    checkedProducts[i] = false;
                }



                prodsInChars = new CharSequence[productList.size()];
                int i=0;
                for(Product prod : productList) {
                    String strProd = prod.getSymbol()+ "  " + prod.getName() + "  " +
                            String.valueOf(prod.getQuantity());
                    // productViews.add(new ProductView(prod.getSymbol(), prod.getName(), prod.getQuantity()));
                    //prodsInString.add(strProd);
                    prodsInChars[i]=strProd;
                    i++;
                    Log.d(getTag(), strProd);
                }
                Log.d("ProdsInCharsSize ", String.valueOf(prodsInChars.length));
                */
            }
        });
    }
   // }


    // public void checkItemInWarehouse() {
    //Intent intent = new Intent(this, ProductInfoActivity.class);
    // startActivity(intent);
    private void productLogin() {


        class ProductLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressBar = (ProgressBar) findViewById(R.id.progressBar);
                //progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the product from the response
                        JSONObject productJson = obj.getJSONObject("object");

                        //creating a product object
                        Product product = new Product(
                                productJson.getInt("id"),
                                productJson.getInt("quantity"),
                                productJson.getString("productname"),
                                productJson.getString("productsymbol")
                        );

                        //storing the product in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).productLogin(product);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProductInfoActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Product does not exist in warehouse database", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("productsymbol", symbol);
                //params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CHECK_PRODUCT, params);
            }
        }

        ProductLogin ul = new ProductLogin();
        ul.execute();
    }

    private void employeeLogin() {


        class EmployeeLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressBar = (ProgressBar) findViewById(R.id.progressBar);
                //progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the product from the response
                        JSONObject employeeJson = obj.getJSONObject("employee");

                        //creating a product object
                        Employee employee = new Employee(
                                employeeJson.getInt("id"),
                                employeeJson.getString("surname"),
                                employeeJson.getString("name"),
                                employeeJson.getString("symbol")
                        );

                        //storing the employee in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).employeeLogin(employee);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), EmployeeInfoActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Employee does not exist in the database", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("symbol", symbol);
                //params.put("password", password);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CHECK_EMPLOYEE, params);
            }
        }

        EmployeeLogin ul = new EmployeeLogin();
        ul.execute();
    }

}
