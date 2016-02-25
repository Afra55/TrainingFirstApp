package com.afra55.trainingfirstapp.module.photobyintent;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);
}
