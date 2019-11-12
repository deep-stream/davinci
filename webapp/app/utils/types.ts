import { RouteComponentProps } from 'react-router-dom'

export interface IRouteParams {
  organizationId?: string
  projectId?: string
  portalId?: string
  dashboardId?: string
  widgetId?: string
  displayId?: string
  viewId?: string
  scheduleId?: string
  uid?: string
}

export type RouteComponentWithParams = RouteComponentProps<IRouteParams, {}>
