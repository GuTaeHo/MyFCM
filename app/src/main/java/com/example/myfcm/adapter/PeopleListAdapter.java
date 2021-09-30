package com.example.myfcm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfcm.R;
import com.example.myfcm.databinding.ItemPeoplelistBinding;
import com.example.myfcm.model.People;

import java.util.ArrayList;

public class PeopleListAdapter extends RecyclerView.Adapter<PeopleListAdapter.ViewHolder> {
    private ArrayList<People> items;
    private OnItemClickListener listener = null;

    public PeopleListAdapter(ArrayList<People> items) {
        this.items = items;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    // 아이템 레이아웃을 메모리에 인플레이션 후 뷰 홀더 생성 및 반환
    public PeopleListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate(삽입될 레이아웃, 부모 레이아웃)
        View view = inflater.inflate(R.layout.item_peoplelist, parent, false);

        // 뷰 홀더 생성
        return new PeopleListAdapter.ViewHolder(view);
    }

    @Override
    // 전달 받은 내용을 각각의 아이템에 출력
    public void onBindViewHolder(@NonNull PeopleListAdapter.ViewHolder holder, int position) {
        People item = items.get(position);

        // holder.binding.ivThumbnail.
        if (holder.binding != null) {
            holder.binding.tvNickname.setText(item.getNickName());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemPeoplelistBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);

            binding.loPeople.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    listener.onItemClick(v, pos);
                }
            });
        }
    }
}
