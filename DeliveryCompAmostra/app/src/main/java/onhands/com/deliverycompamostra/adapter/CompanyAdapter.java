package onhands.com.deliverycompamostra.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import onhands.com.deliverycompamostra.adapter.CompanyViewHolder;

import onhands.com.deliverycompamostra.ListCallback;
import onhands.com.deliverycompamostra.R;
import onhands.com.deliverycompamostra.model.Company;

/**
 * Created by rodrigocavalcante on 5/30/16.
 */
public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder>{

    ListCallback callback;
    List<Company> companies;

    public CompanyAdapter(ListCallback callback) {
        this.callback = callback;
        companies = new ArrayList<>();
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    @Override
    public CompanyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_company, parent, false);

        return new CompanyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CompanyViewHolder holder, final int position) {
        final Company company = companies.get(position);

        Glide.with(holder.mView.getContext())
                .load(company.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .dontAnimate()
                .into(holder.image);

        holder.name.setText(company.getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onCompanyClick(company);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }
}
