package com.raywenderlich.reposearch;

import java.util.ArrayList;

/**
 * Created by BG on 2017.12.07..
 */

public interface DownloadCompleteListener {

    void downloadComplete(ArrayList<Repository> repositories);
}
