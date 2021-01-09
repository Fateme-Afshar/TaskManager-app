package com.example.taskmaneger.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.taskmaneger.R;
import com.example.taskmaneger.adapter.AdminAdapter;
import com.example.taskmaneger.databinding.FragmentAdminBinding;
import com.example.taskmaneger.model.User;
import com.example.taskmaneger.viewModel.AdminViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {
    private FragmentAdminBinding mBinding;
    private AdminViewModel mViewModel;

    private AdminAdapter mAdapter;
    private AdminFragmentCallback mCallback;

    public AdminFragment() {
        // Required empty public constructor
    }


    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AdminFragmentCallback)
            mCallback=(AdminFragmentCallback) context;
        else
            throw new ClassCastException
                    ("Must Implement AdminFragmentCallback interface");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel=new ViewModelProvider(this).get(AdminViewModel.class);
        mViewModel.getUserListLiveData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> userList) {
                setupAdapter(userList);
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_admin,
                container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_admin, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_admin_logout:
                mCallback.onLogoutMenuItemClickListener();
                return true;
            case R.id.menu_admin_delete:
                mViewModel.deleteAll();
                mBinding.notifyChange();
                return true;
            default:
                return false;
        }
    }

    private void setupAdapter(List<User> userList) {
        mAdapter=new AdminAdapter(userList, getContext(), new AdminAdapter.AdminAdapterCallback() {
            @Override
            public void onItemUserSelected(long userId) {
                mCallback.onItemUserSelected(userId);
            }

            @Override
            public void onTrashCanClickListener(User user) {
                mViewModel.deleteUser(user);
                mBinding.notifyChange();
            }
        });

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBinding.recyclerView.setAdapter(mAdapter);
    }

    public interface AdminFragmentCallback{
        void onItemUserSelected(long userId);
        void onLogoutMenuItemClickListener();
    }
}