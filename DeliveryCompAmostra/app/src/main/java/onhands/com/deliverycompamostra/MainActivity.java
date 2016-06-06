package onhands.com.deliverycompamostra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import onhands.com.deliverycompamostra.adapter.CompanyAdapter;
import onhands.com.deliverycompamostra.model.Company;
import onhands.com.deliverycompamostra.response.CompanyResponse;
import onhands.com.deliverycompamostra.webservice.WebService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ListCallback {

    @BindView(R.id.recycleView)
    RecyclerView list;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CompanyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        adapter = new CompanyAdapter(this);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setLayoutManager(new LinearLayoutManager(list.getContext()));
        //list.setItemAnimator(new SlideInUpAnimator());
        list.setAdapter(adapter);

        load();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refreshing data on server
                load();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);

    }

    @Override
    public void onCompanyClick(final Company company) {
        new MaterialDialog.Builder(this)
                .title(company.getName())
                .items(company.getPhones())
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        if (which >= 0) {

                            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                String url = "tel:" + company.getPhones()[which].replace("-", "");

                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                dialog.dismiss();
                            } else {
                                getPermission();
                                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.not_allowed, Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }
                        return true;
                    }
                })
                .positiveText(R.string.call)
                .show();
    }

    public void load(){
        WebService.getInstance().getService().getCompanies().enqueue(new Callback<CompanyResponse>() {
            @Override
            public void onResponse(Call<CompanyResponse> call, Response<CompanyResponse> response) {

                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

                if (response.code() != 200) {
                    Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.default_failure, Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if(response.body().getStatus() == null) {
                    Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.default_failure, Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                if (response.body().getStatus().getCode() != 200) {
                    System.out.println(response.body().getStatus().getCode());
                    Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), response.body().getStatus().getMessage(), Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }


                adapter.setCompanies(response.body().getCompanies());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CompanyResponse> call, Throwable t) {
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);

                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), R.string.default_failure, Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }

    private final int CALL_REQUEST_CODE = 0;

    //permissao de ligar
    public void getPermission() {
        if ((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST_CODE);

        }
    }
}
