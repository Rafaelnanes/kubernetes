{{/*
Define a helper to get the full name of a resource, including a release name and the chart name.
This ensures resource names are unique across Helm releases.
*/}}
{{- define "my-helm-app.fullname" -}}
{{- printf "%s-%s" .Release.Name .Chart.Name | trunc 63 | trimSuffix "-" -}}
{{- end }}