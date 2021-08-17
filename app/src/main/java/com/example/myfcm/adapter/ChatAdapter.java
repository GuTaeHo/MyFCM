package com.example.myfcm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfcm.R;
import com.example.myfcm.activity.MainActivity;
import com.example.myfcm.databinding.ItemChatBinding;
import com.example.myfcm.firebase.MyFirebaseMessagingService;
import com.example.myfcm.model.Item;
import com.example.myfcm.util.TimeUtil;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private ArrayList<Item> items;

    public ChatAdapter(ArrayList<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    // 아이템 레이아웃을 메모리에 인플레이션 후 뷰 홀더 생성 및 반환
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflate(삽입될 레이아웃, 부모 레이아웃)
        View view = inflater.inflate(R.layout.item_chat, parent, false);

        // 뷰 홀더 생성
        return new ViewHolder(view);
    }

    @Override
    // 전달 받은 내용을 각각의 아이템에 출력
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);

        holder.binding.tvContents.setText(item.getContents());
        holder.binding.tvTimeSend.setText(TimeUtil.getCustomTime(item.getTime()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
