package com.example.coinrabit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter <ListAdapter.ViewHolder> {

    private List<ListElement> mData;
    private LayoutInflater mInflater;
    private Context context;

    public ListAdapter(List<ListElement> itemList,Context context){
        this.mInflater=LayoutInflater.from(context);
        this.context=context;
        this.mData=itemList;

    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element,null);
        return new ListAdapter.ViewHolder(view);

    }
    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        holder.bindData(mData.get(position));
        holder.idMovimiento.setText(mData.get(position).getIdMovimiento());
        holder.concepto.setText(mData.get(position).getConcepto());
        holder.fecha.setText(mData.get(position).getFecha());
        holder.tipo.setText(mData.get(position).getTipo());

        // set Events
        holder.setOnClickListeners();


    }

    public void setItems(List<ListElement> items){mData = items;}


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iconImage;
        TextView tipo,concepto,fecha,monto,idMovimiento;
        Button btnDetalles;
        Context context;

        ViewHolder(View itemView){
            super(itemView);
            context=itemView.getContext();
            iconImage = itemView.findViewById(R.id.iconImageView);
            tipo=itemView.findViewById(R.id.tipoTextView);
            concepto=itemView.findViewById(R.id.conceptoTextView);
            fecha=itemView.findViewById(R.id.fechaTextView);
            monto=itemView.findViewById(R.id.montoTextView);
            idMovimiento=itemView.findViewById(R.id.idMovimientoTextView);
            btnDetalles=itemView.findViewById(R.id.btnDetalles);
        }

        void setOnClickListeners(){
            btnDetalles.setOnClickListener(this);
        }

        void bindData(final ListElement item){
            iconImage.setColorFilter(Color.parseColor(item.getColorIcono()), PorterDuff.Mode.SRC_IN);
            iconImage.setImageResource(item.getIcono());
            tipo.setText(item.getTipo());
            concepto.setText(item.getConcepto());
            fecha.setText(item.getFecha());
            monto.setText(item.getMonto());
            monto.setTextColor(Color.parseColor(item.getColorMonto()));
            idMovimiento.setText(item.getIdMovimiento());


        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,detailsCardView.class);
            intent.putExtra("idMovimiento",idMovimiento.getText());
            context.startActivity(intent);
        }
    }


}
