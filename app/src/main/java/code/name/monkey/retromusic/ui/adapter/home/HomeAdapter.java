package code.name.monkey.retromusic.ui.adapter.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.retro.musicplayer.backend.model.Album;
import com.retro.musicplayer.backend.model.Artist;
import com.retro.musicplayer.backend.model.Playlist;

import java.util.ArrayList;

import butterknife.BindView;
import code.name.monkey.retromusic.R;
import code.name.monkey.retromusic.ui.adapter.PlaylistAdapter;
import code.name.monkey.retromusic.ui.adapter.album.AlbumAdapter;
import code.name.monkey.retromusic.ui.adapter.artist.ArtistAdapter;
import code.name.monkey.retromusic.ui.adapter.base.MediaEntryViewHolder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by hemanths on 19/07/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SUB_HEADER = 0;
    private static final int DATA = 1;
    private ArrayList<Object> dataSet = new ArrayList<>();
    private AppCompatActivity activity;
    private CompositeDisposable mDisposable;

    public HomeAdapter(@NonNull AppCompatActivity activity) {
        this.activity = activity;
        mDisposable = new CompositeDisposable();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.activity)
                .inflate(i == 0 ? R.layout.sub_header : R.layout.recycler_view_sec, viewGroup, false));
    }

    @Override
    public int getItemViewType(int position) {
        if (dataSet.get(position) instanceof String) {
            return SUB_HEADER;
        } else if (dataSet.get(position) instanceof ArrayList) {
            return DATA;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
        ViewHolder viewholder = (ViewHolder) holder;
        switch (getItemViewType(i)) {
            case SUB_HEADER:
                String title = (String) dataSet.get(i);
                if (viewholder.title != null) {
                    viewholder.title.setText(title);
                }
                break;
            case DATA:
                ArrayList arrayList = (ArrayList) dataSet.get(i);
                Object something = arrayList.get(0);
                if (something instanceof Artist) {
                    if (viewholder.recyclerView != null) {
                        viewholder.recyclerView.setLayoutManager(new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false));
                        viewholder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                        viewholder.recyclerView.setAdapter(new ArtistAdapter(activity, (ArrayList<Artist>) arrayList, R.layout.item_artist, false, null));
                    }
                } else if (something instanceof Album) {
                    if (viewholder.recyclerView != null) {
                        viewholder.recyclerView.setLayoutManager(new GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false));
                        viewholder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                        viewholder.recyclerView.setAdapter(new AlbumAdapter(activity, (ArrayList<Album>) arrayList, R.layout.item_image, false, null));
                    }
                } else if (something instanceof Playlist) {
                    if (viewholder.recyclerView != null) {
                        viewholder.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                        viewholder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                        viewholder.recyclerView.setAdapter(new PlaylistAdapter(activity, (ArrayList<Playlist>) arrayList, R.layout.item_list, null));
                    }
                }
                break;
        }
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void swapData(@NonNull ArrayList<Object> data) {
        dataSet = data;
        notifyDataSetChanged();
    }

    public class ViewHolder extends MediaEntryViewHolder {
        @BindView(R.id.see_all)
        @Nullable
        TextView seeAll;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
