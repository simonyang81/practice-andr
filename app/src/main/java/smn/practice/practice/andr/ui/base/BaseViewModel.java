package smn.practice.practice.andr.ui.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BaseViewModel extends AndroidViewModel {

    protected final CompositeDisposable disposables = new CompositeDisposable();

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        // 清理RxJava订阅
        if (!disposables.isDisposed()) {
            disposables.clear();
        }
    }

}
