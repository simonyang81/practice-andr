package smn.practice.practice.smn.library.log;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import smn.practice.practice.smn.library.R;

public class SMNViewPrinter implements SMNLogPrinter {

    private final RecyclerView recyclerView;
    private final LogAdapter adapter;
    private final SMNViewPrinterProvider provider;

    public SMNViewPrinter(Activity activity) {

        FrameLayout rootView = activity.findViewById(android.R.id.content);

        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(recyclerView.getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        provider = new SMNViewPrinterProvider(rootView, recyclerView);

    }

    public SMNViewPrinterProvider getViewProvider() {
        return this.provider;
    }

    @Override
    public void print(@NonNull SMNLogConfig config, int level, String tag, @NonNull String printString) {
        adapter.addLogEntry(
                new SMNLogModel(System.currentTimeMillis(), level, tag, printString)
        );
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    /**
     * RecyclerView适配器，用于显示日志条目
     */
    public static class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
        private final List<SMNLogModel> logEntries;
        private final LayoutInflater layoutInflater;

        /**
         * 构造方法
         */
        public LogAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
            this.logEntries = new ArrayList<>();
        }
        
        /**
         * 添加日志条目
         *
         * @param logEntry 日志实体
         */
        public void addLogEntry(SMNLogModel logEntry) {
            this.logEntries.add(logEntry);
            notifyItemChanged(this.logEntries.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.smnlog_item, parent, false);
            return new LogViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
            SMNLogModel logModel = this.logEntries.get(position);
            holder.bind(logModel);
        }
        
        @Override
        public int getItemCount() {
            return logEntries.size();
        }
        
        /**
         * ViewHolder类，用于保存日志条目视图
         */
        static class LogViewHolder extends RecyclerView.ViewHolder {
            private final TextView tagText;
            private final TextView messageText;
            
            /**
             * 构造方法
             * 
             * @param itemView 条目视图
             */
            public LogViewHolder(@NonNull View itemView) {
                super(itemView);
                tagText = itemView.findViewById(R.id.tag);
                messageText = itemView.findViewById(R.id.message);
            }
            
            /**
             * 绑定日志条目数据到视图
             * 
             * @param entry 日志条目
             */
            public void bind(SMNLogModel entry) {
                tagText.setText(entry.getFlattened());
                messageText.setText(entry.log);
                
                // 根据日志级别设置不同颜色
                int color = getLevelColor(entry.level);
                tagText.setTextColor(color);
                messageText.setTextColor(color);
            }
            
            /**
             * 获取日志级别对应的颜色
             * 
             * @param level 日志级别
             * @return 颜色值
             */
            private int getLevelColor(int level) {
                switch (level) {
                    case 2: return 0xFF808080; // VERBOSE - 灰色
                    case 3: return 0xFF0000FF; // DEBUG - 蓝色
                    case 4: return 0xFF008000; // INFO - 绿色
                    case 5: return 0xFFFF8000; // WARN - 橙色
                    case 6: return 0xFFFF0000; // ERROR - 红色
                    default: return 0xFF000000; // UNKNOWN - 黑色
                }
            }
        }
    }
    
}
