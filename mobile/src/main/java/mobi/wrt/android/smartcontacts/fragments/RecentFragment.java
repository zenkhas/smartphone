package mobi.wrt.android.smartcontacts.fragments;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import by.istin.android.xcore.fragment.collection.RecyclerViewFragment;
import by.istin.android.xcore.model.CursorModel;
import mobi.wrt.android.smartcontacts.R;
import mobi.wrt.android.smartcontacts.fragments.adapter.RecentAdapter;
import mobi.wrt.android.smartcontacts.helper.ContactHelper;

/**
 * Created by IstiN on 31.01.2015.
 */
public class RecentFragment extends RecyclerViewFragment<RecentAdapter.Holder, RecentAdapter, RecentFragment.RecentModel> {

    public static final String ORDER = CallLog.Calls.DATE + " DESC";
    public static final String[] PROJECTION = new String[]{CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.DATE, CallLog.Calls.CACHED_NUMBER_TYPE, CallLog.Calls.TYPE};

    public static class RecentModel extends CursorModel {

        public RecentModel(Cursor cursor) {
            super(cursor);
        }

        public RecentModel(Cursor cursor, boolean isMoveToFirst) {
            super(cursor, isMoveToFirst);
        }

        @Override
        public void doInBackground(Context context) {
            super.doInBackground(context);
            if (!isEmpty()) {
                ContactHelper contactHelper = ContactHelper.get(context);
                for (int i = 0; i < size(); i++) {
                    contactHelper.initPhotoUri(get(i).getString(CallLog.Calls.NUMBER));
                }
            }
        }

    }


    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
    }

    @Override
    public CursorModel.CursorModelCreator<RecentModel> getCursorModelCreator() {
        return new CursorModel.CursorModelCreator<RecentModel>() {
            @Override
            public RecentModel create(Cursor cursor) {
                return new RecentModel(cursor);
            }
        };
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_recent;
    }

    @Override
    public String[] getProjection() {
        return PROJECTION;
    }

    @Override
    public RecentAdapter createAdapter(FragmentActivity fragmentActivity, RecentModel cursor) {
        return new RecentAdapter(cursor);
    }

    @Override
    public void swap(RecentAdapter recentAdapter, RecentModel cursor) {
        getAdapter().swap(cursor);
    }

    @Override
    public String getSelection() {
        return null;
    }

    @Override
    public String getOrder() {
        return ORDER + " LIMIT 15";
    }

    @Override
    public Uri getUri() {
        return CallLog.Calls.CONTENT_URI;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getProcessorKey() {
        return null;
    }
}
