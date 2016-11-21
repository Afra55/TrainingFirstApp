package com.afra55.trainingfirstapp.module.photo_by_intent;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);
}
