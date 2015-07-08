package com.ataulm.basic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class ProductsAdapter extends BaseAdapter {

    private final ProductDataSet productDataSet;
    private final ProductClickListener productClickListener;
    private final LayoutInflater layoutInflater;

    ProductsAdapter(ProductDataSet productDataSet, ProductClickListener productClickListener, LayoutInflater layoutInflater) {
        this.productDataSet = productDataSet;
        this.productClickListener = productClickListener;
        this.layoutInflater = layoutInflater;
    }

    @Override
    public int getCount() {
        return productDataSet.size();
    }

    @Override
    public Product getItem(int position) {
        return productDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productDataSet.getId(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = createView(parent);
            view.setTag(ViewHolder.from(view));
        }
        Product product = productDataSet.get(position);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        update(viewHolder, product);
        return view;
    }

    private View createView(ViewGroup parent) {
        return layoutInflater.inflate(R.layout.view_product, parent, false);
    }

    private void update(ViewHolder viewHolder, final Product product) {
        viewHolder.name.setText(product.name);
        viewHolder.quantity.setText(String.valueOf(product.quantity));
        viewHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onMinusClick(product);
            }
        });
        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productClickListener.onPlusClick(product);
            }
        });
    }

    private static final class ViewHolder {
        final TextView name;
        final TextView quantity;
        final View minus;
        final View plus;

        static ViewHolder from(View view) {
            return new ViewHolder(
                    ((TextView) view.findViewById(R.id.product_name)),
                    ((TextView) view.findViewById(R.id.product_quantity)),
                    view.findViewById(R.id.product_minus),
                    view.findViewById(R.id.product_plus)

            );
        }

        private ViewHolder(TextView name, TextView quantity, View minus, View plus) {
            this.name = name;
            this.quantity = quantity;
            this.minus = minus;
            this.plus = plus;
        }
    }

}
