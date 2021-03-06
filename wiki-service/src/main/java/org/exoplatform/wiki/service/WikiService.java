/*
 * Copyright (C) 2003-2008 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.wiki.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.exoplatform.commons.utils.PageList;
import org.exoplatform.container.component.ComponentPlugin;
import org.exoplatform.wiki.mow.api.DraftPage;
import org.exoplatform.wiki.mow.api.Page;
import org.exoplatform.wiki.mow.core.api.wiki.PageImpl;
import org.exoplatform.wiki.mow.core.api.wiki.Template;
import org.exoplatform.wiki.mow.core.api.wiki.TemplateContainer;
import org.exoplatform.wiki.service.listener.PageWikiListener;
import org.exoplatform.wiki.service.search.SearchResult;
import org.exoplatform.wiki.service.search.TemplateSearchData;
import org.exoplatform.wiki.service.search.TemplateSearchResult;
import org.exoplatform.wiki.service.search.TitleSearchResult;
import org.exoplatform.wiki.service.search.WikiSearchData;
import org.exoplatform.wiki.template.plugin.WikiTemplatePagePlugin;

/**
 * Created by The eXo Platform SARL.
 * <p>
 * WikiService is interface provide functions for processing database
 * with wikis and pages include: add, edit, remove and searching data
 * 
 * @author  exoplatform
 * @since   Mar 04, 2010
 */
public interface WikiService {

  public Page createPage(String wikiType, String wikiOwner, String title, String parentId) throws Exception;

  public Template createTemplatePage(String title, WikiPageParams params) throws Exception;

  public void initDefaultTemplatePage(String path) ;

  public boolean deletePage(String wikiType, String wikiOwner, String pageId) throws Exception;

  public void deleteTemplatePage(String wikiType, String wikiOwner, String templateId) throws Exception;

  public void deleteDraftNewPage(String draftNewPageId) throws Exception;

  public boolean renamePage(String wikiType, String wikiOwner, String pageName, String newName, String newTitle) throws Exception;

  public boolean movePage(WikiPageParams currentLocationParams, WikiPageParams newLocationParams) throws Exception;

  public List<PermissionEntry> getWikiPermission(String wikiType, String wikiOwner) throws Exception;

  public void setWikiPermission(String wikiType, String wikiOwner, List<PermissionEntry> permissionEntries) throws Exception;

  public Page getPageById(String wikiType, String wikiOwner, String pageId) throws Exception;
  
  public Page getPageByRootPermission(String wikiType, String wikiOwner, String pageId) throws Exception;

  public Page getRelatedPage(String wikiType, String wikiOwner, String pageId) throws Exception;

  public Page getExsitedOrNewDraftPageById(String wikiType, String wikiOwner, String pageId) throws Exception;

  public Page getPageByUUID(String uuid) throws Exception;

  public Template getTemplatePage(WikiPageParams params, String templateId) throws Exception;

  public PageList<SearchResult> searchContent(WikiSearchData data) throws Exception;

  public List<BreadcrumbData> getBreadcumb(String wikiType, String wikiOwner, String pageId) throws Exception;

  public WikiPageParams getWikiPageParams(BreadcrumbData data) throws Exception;

  public PageList<SearchResult> search(WikiSearchData data) throws Exception;

  public List<TemplateSearchResult> searchTemplate(TemplateSearchData data) throws Exception;

  public List<SearchResult> searchRenamedPage(String wikiType, String wikiOwner, String pageId) throws Exception;

  public List<TitleSearchResult> searchDataByTitle(WikiSearchData data) throws Exception;

  public Object findByPath(String path, String objectNodeType) throws Exception;

  public String getDefaultWikiSyntaxId();

  /**
   * Get the draft save sequence time from config file
   * 
   * @return The save draft sequence time
   */
  public long getSaveDraftSequenceTime();

  public String getPageTitleOfAttachment(String path) throws Exception;

  public InputStream getAttachmentAsStream(String path) throws Exception;

  public PageImpl getHelpSyntaxPage(String syntaxId) throws Exception;
  
  public Page getMetaDataPage(MetaDataPage metaPage) throws Exception;

  public Map<String, Template> getTemplates(WikiPageParams params) throws Exception;

  public TemplateContainer getTemplatesContainer(WikiPageParams params) throws Exception;

  public void modifyTemplate(WikiPageParams params, Template template, String newName, String newDescription, String newContent, String newSyntaxId) throws Exception;

  public boolean isExisting(String wikiType, String wikiOwner, String pageId) throws Exception;

  /**
   * Get wiki default permission
   * 
   * @param wikiType The type of wiki
   * @param wikiOwner The owner of wiki
   * @return The permisison list for wiki
   * @throws Exception
   */
  public List<String> getWikiDefaultPermissions(String wikiType, String wikiOwner) throws Exception;
  
  /**
   * register a {@link PageWikiListener} 
   * @param listener
   */
  public void addComponentPlugin(ComponentPlugin plugin);

  public void addWikiTemplatePagePlugin(WikiTemplatePagePlugin templatePlugin);

  /**
   * @return list of {@link PageWikiListener}
   */
  public List<PageWikiListener> getPageListeners();

  public boolean addRelatedPage(WikiPageParams orginaryPageParams, WikiPageParams relatedPageParams) throws Exception;

  public List<Page> getRelatedPage(WikiPageParams pageParams) throws Exception;

  public boolean removeRelatedPage(WikiPageParams orginaryPageParams, WikiPageParams relatedPageParams) throws Exception;
  
  /**
   * Create a draft page for a wiki page which is specified by the wiki page param
   * 
   * @param param wiki page param
   * @param revision the target revision, null if it's the lastest revision
   * @param clientTime The time of client when save draft
   * @return draft page
   * @throws Exception if create draft not success
   */
  public DraftPage createDraftForExistPage(WikiPageParams param, String revision, long clientTime) throws Exception;
  
  /**
   * Create a draft page for a new wiki page which parent is specified by the wiki page param
   * 
   * @param param parent wiki page param
   * @param clientTime The time of client when save draft
   * @return draft page
   * @throws Exception if create draft not success
   */
  public DraftPage createDraftForNewPage(WikiPageParams parentPageParam, long clientTime) throws Exception;
  
  /**
   * Achieve a draft page for a wiki page which is specified by the wiki page param
   * 
   * @param param wiki page param
   * @return draft page or null if draft page doesn't exist.
   * @throws Exception
   */
  public DraftPage getDraft(WikiPageParams param) throws Exception;
  
  /**
   * Get draft by draft name
   * 
   * @param draftName draft name
   * @return draft page or null if draft page doesn't exist.
   * @throws Exception
   */
   public DraftPage getDraft(String draftName) throws Exception;
  
  /**
    * Remove a draft page for a wiki page which is specified by the wiki page param
    * 
    * @param param wiki page param
    * @throws Exception
    */
  public void removeDraft(WikiPageParams param) throws Exception;
  
  /**
   * Remove a draft page by draft name
   * 
   * @param draftName draft name
   * @throws Exception
   */
  public void removeDraft(String draftName) throws Exception;
  
  /**
   * Get collection of draft page belong to a user
   * 
   * @param username user name
   * @return draft list of user
   * @throws Exception
   */
  public List<DraftPage> getDrafts(String username) throws Exception;
  
  /**
   * Get wiki page by page UUID
   * 
   * @param uuid of wiki page
   * @return wiki page
   * @throws Exception
   */
  public Page getWikiPageByUUID(String uuid) throws Exception;
  
  /**
   * Get the draft that's created lastest
   * 
   * @return lastest draft
   * @throws Exception
   */
  public DraftPage getLastestDraft() throws Exception;

  /**
   * Get the uri of wiki webapp
   * 
   * @return The uri of wiki webapp
   */
  public String getWikiWebappUri();
  
  /**
   * Checks whether a user is a space's member or not.
   *
   * @param spaceId  the existing space id
   * @param userId the remote user id
   * @return true if that user is a member; otherwise, false
   */
  public boolean isSpaceMember(String spaceId, String userId);
  
  /**
   * Check if the space is hidden or not
   * 
   * @param groupId The group Id to check
   * @return the space is hidden or not
   * @throws Exception
   */
  public boolean isHiddenSpace(String groupId) throws Exception;
   
  /** 
   * Check if the current user has addmin permission on the space
   * 
   * @param wikiType The wiki type of the space
   * @param owner The owner of the space
   * @return current user has addmin permisison on the space or not
   * @throws Exception
   */
  public boolean hasAdminSpacePermission(String wikiType, String owner) throws Exception;
  
  /**
   * Check if the current user has addmin permission on the page
   * 
   * @param wikiType The wiki type of the space
   * @param owner The owner of the space
   * @return current user has addmin permisison on the page or not
   * @throws Exception
   */
  public boolean hasAdminPagePermission(String wikiType, String owner) throws Exception;
}
